package org.openaod.patternhouse;

import org.openaod.patternhouse.util.FireExtensions;

import java.util.Vector;

public class PatternBrowserBuilder extends FireExtensions {

    private static final String inputImagesDir = "./Assets/patterns/";
    private static final String sourcesDir = "./Sources/Code/";
    // Future feature : private static final String metadataDir = "./Sources/PatternHouse/Meta/";
    private static final String outputDir = "./Portal/portal/";
    private static final String templatesDir = "./Templates/";
    private static final String templateName = "SourceTemplate.md";
    private static final String outputExtension = ".md";
    private static final String[] languages = { "C" , "C++" , "CSharp" , "Python" , "Java" };
    private static final String[] languagesExtensions = { "c" , "cpp" , "cs" , "py" , "java" };
    private static final String[] markdownFormattedLanguagesExtensions = { "c" , "cpp" , "cs" , "python" , "java" };
    private static final String[] goals = { "alphabetic", "numeric", "pyramid", "series", "spiral", "string", "symbol", "wave" };

    private static final String[] fileNotFoundMessage = {
            ":::tip",
            "We are currently awaiting contributions on this pattern for this language. Please check back later or contribute to this pattern.",
            ":::"
    };
    private static final String[] languageSourceEnclosing = {
            "```$FIRE(MK_LANG_EX) title=\"$FIRE(PATTERN_SOURCE_NAME)\"",
            "$FIRE_LOAD_SOURCE()",
            "```"
    };
    private static final String languageDeclarationPrefix = "## ";

    public static void build() {

        // Ensure build directory is present and ready
        mkdir(outputDir);

        // Load the template source into VCache memory
        int TEMPLATE_INDEX = read(templatesDir+templateName);

        // Loop through all the goals
        for(String goal:goals) {

            // Get all possible pattern images for the goal
            int GOAL_PATTERNS_INDEX = listFiles(inputImagesDir+goal);
            
            // Loop through all the possible pattern images for the goal
            for(String patternImage:get(GOAL_PATTERNS_INDEX)) {

                // Skip and continue if pattern file is not an image
                if( !(patternImage.endsWith(".PNG") || patternImage.endsWith(".png") || patternImage.endsWith(".JPG") || patternImage.endsWith(".jpg") ) ) continue;

                // Replace extensions in the name
                String replacedPatternImage = patternImage.replace(".PNG", "").replace(".png", "").replace(".JPG", "").replace(".jpg","");

                // Create a Generated Pattern Name for the patternImage
                // This will be in the format of (Capitalized Goal Name) + " Pattern " + (Numeric Part of patternImage)
                // eg. "Alphabetic Pattern 20"
                String capGoal = goal.substring(0, 1).toUpperCase() + goal.substring(1);
                String genPatternName = capGoal + " Pattern " + replacedPatternImage.replaceAll("\\D+","");

                // Create a vector based on template
                Vector<String> outputVector = new Vector<>(1,1);

                // Loop through template source and replace Fire variables
                for(String templateString:get(TEMPLATE_INDEX)) {
                    String processedTString = templateString.replace("$FIRE(GENERATED_PATTERN_NAME)", genPatternName).replace("$FIRE(GOAL)", goal).replace("$FIRE(PATTERN_NAME)", patternImage.replace("Assets", "assets").substring(1));
                    outputVector.addElement(processedTString);
                }

                // Loop through all languages
                for(int i=0;i<languages.length;i++) {

                    outputVector.addElement(languageDeclarationPrefix + languages[i]);

                    // Get language suffix with pattern name
                    String sourceFile = replacedPatternImage + "." + languagesExtensions[i];
                    String sourceFilePath = sourcesDir + markdownFormattedLanguagesExtensions[i] + "/" + goal + "/" + getProperName(sourceFile);

                    // Check for source code for the pattern in the language
                    if(exists(sourceFilePath)) {
                        // Loads the enclosing statements for the languages and does the replacements accordingly
                        for(String st:languageSourceEnclosing) {
                            if(st.equals("$FIRE_LOAD_SOURCE()")) for(String src:get(read(sourceFilePath))) outputVector.addElement(src);
                            else outputVector.addElement(st.replace("$FIRE(MK_LANG_EX)", markdownFormattedLanguagesExtensions[i]).replace("$FIRE(PATTERN_SOURCE_NAME)",sourceFile));
                        }
                    }

                    // If the source is not found then include the FileNotFound message
                    else {
                        for(String st:fileNotFoundMessage) outputVector.addElement(st);
                    }
                }

                // Write the Generated file for the pattern
                String genGoalDir = capGoal + " Patterns";
                mkdir(outputDir+genGoalDir+"/");
                write(outputDir+genGoalDir+"/"+genPatternName+outputExtension, outputVector);
            }

        }
    }

}
