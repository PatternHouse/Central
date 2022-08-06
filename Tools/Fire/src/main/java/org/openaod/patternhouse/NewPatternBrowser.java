package org.openaod.patternhouse;

import org.openaod.patternhouse.util.FireExtensions;

import java.util.Vector;

public class NewPatternBrowser extends FireExtensions {

    /* Pattern images URL prefix */
    private static final String imgURLprefix = "https://cdn.openaod.org/patternhouse/images/patterns/";

    /* Pattern GH Links URL prefix */
    private static final String ghURLprefix = "https://github.com/openAOD/";

    /* Directory for pattern images */
    private static final String inputImagesDir = "./Assets/patterns/";

    /* Directory for code files */
    private static final String sourcesDir = "./Sources/Code/";

    /* Output directory */
    private static final String outputDir = "./Portal/portal/";

    /* Directory for templates */
    private static final String templatesDir = "./Templates/";

    /* Primary source template directory with name */
    private static final String pSourceTemplatePath = "SourceDisplay/Template.html";

    /* Target file extenstion */
    private static final String outputExtension = ".html";

    /* Display Language Tags */
    private static final String[] dispLanguages = { "C" , "C++" , "C#" , "Python" , "Java" };

    /* GitHub Language Tags */
    private static final String[] ghLangTags = { "C" , "CPlusPlus" , "CSharp" , "Python" , "Java" };

    /* Language Extensions for : Searching for Code Files and PrismJS Languages Tags */
    private static final String[] languagesExtensions = { "c" , "cpp" , "cs" , "py" , "java" };

    /* Target Goals */
    private static final String[] goals = { "alphabetic", "numeric", "pyramid", "series", "spiral", "string", "symbol", "wave" };

    /* Fire Variable <=> Name Map */
    private static final Vector<String> fireVariableName = new Vector<>(1,1);
    private static final Vector<String> fireVariableValue = new Vector<>(1,1);

    public static void build() {

        /* Checks to ensure that template is found */
        if(!exists(templatesDir+pSourceTemplatePath)) {
            System.out.println("CHECK [FAIL]: Primary source template not found.");
            System.out.println("CHECK [FAIL]: Searched file : "+(templatesDir+pSourceTemplatePath));
            System.exit(101);
        }

        // Ensure build directory is present and ready
        mkdir(outputDir);

        // Load the template source into VCache memory
        int P_TEMPLATE_INDEX = read(templatesDir+pSourceTemplatePath);

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
                int patternNumber = Integer.parseInt(replacedPatternImage.replaceAll("\\D+",""));
                String genPatternName = capGoal + " Pattern " + patternNumber;

                /* VCache Vector of Source Files */
                int[] SRC_INDEX = new int[dispLanguages.length];

                // Loop through all languages
                for(int i=0;i<dispLanguages.length;i++) {

                    // Get language suffix with pattern name
                    String sourceFile = replacedPatternImage + "." + languagesExtensions[i];
                    String sourceFilePath = sourcesDir + languagesExtensions[i] + "/" + goal + "/" + getProperName(sourceFile);

                    // Check for source code for the pattern in the language
                    if(exists(sourceFilePath)) {
                        SRC_INDEX[i] = read(sourceFilePath);
                    } else {
                        SRC_INDEX[i] = -1;
                    }
                }

                /* Unsupported Languages String (FIRE_UNSUP_LANG) */
                String unsupLang = "";

                for(int i=0; i < dispLanguages.length; i++) {
                    if(SRC_INDEX[i] == -1) unsupLang += " "+dispLanguages[i];
                    unsupLang = unsupLang.trim();
                }

                /* Calculate Fire Variables */

                String imgURL = imgURLprefix + goal + "/" + patternImage;

                String prevPStatus = "";
                String nextPStatus = "";
                String prevGenPatternName = "#";
                String nextGenPatternName = "#";

                if(patternNumber == 1) { prevPStatus = "disabled"; }
                else {
                    int prevPNo = patternNumber - 1;
                    prevGenPatternName = capGoal + " Pattern " + prevPNo;
                }

                if(patternNumber == get(GOAL_PATTERNS_INDEX).size()) { nextPStatus = "disabled"; }
                else {
                    int nextPNo = patternNumber + 1;
                    nextGenPatternName = capGoal + " Pattern " + nextPNo;
                }

                String[] langStatus = new String[dispLanguages.length];
                for(int i=0; i < dispLanguages.length; i++) {
                    if(SRC_INDEX[i] == -1) langStatus[i] = "inactive";
                    else langStatus[i] = "";
                }

                String noteFlag = "";

                if(unsupLang.equals("")) noteFlag = "disabled";

                /* Map Fire Variables */

                // Fire Generated Pattern Name
                fireVariableName.addElement("$(FIRE_GNPN)");
                fireVariableValue.addElement(genPatternName);

                // Fire Display Image URL
                fireVariableName.addElement("$(FIRE_IMG_URL)");
                fireVariableValue.addElement(imgURL);

                // Fire Pattern Group Name
                fireVariableName.addElement("$(FIRE_PNGRP)");
                fireVariableValue.addElement(capGoal + " Patterns");

                // Fire Langauage Statuses
                for(int i=0;i<dispLanguages.length;i++) {
                    String upLang = dispLanguages[i].toUpperCase();
                    fireVariableName.addElement("$(FIRE_"+ upLang +"_LANG_SUP)");
                    fireVariableValue.addElement(langStatus[i]);
                }

                // Fire Previous Pattern Link and Status
                fireVariableName.addElement("$(FIRE_PREV_PNLNK)");
                fireVariableValue.addElement(prevGenPatternName+outputExtension);
                fireVariableName.addElement("$(FIRE_PREV_STATUS)");
                fireVariableValue.addElement(prevPStatus);

                // Fire Next Pattern Link and Status
                fireVariableName.addElement("$(FIRE_NEXT_PNLNK)");
                fireVariableValue.addElement(nextGenPatternName+outputExtension);
                fireVariableName.addElement("$(FIRE_NEXT_STATUS)");
                fireVariableValue.addElement(nextPStatus);

                // Fire ending note status
                fireVariableName.addElement("$(FIRE_NTDSP_FLG)");
                fireVariableValue.addElement(noteFlag);

                /* Prepare the template */
                Vector<String> outputVector = new Vector<>(1,1);
                Vector<String> sourceRepeatTemplate = new Vector<>(1,1);
                boolean redirect = false;
                for(String tstring:get(P_TEMPLATE_INDEX)) {
                    if(tstring.trim().equals("$(FIRE_SRC_RPT_START)")) { redirect = true; continue; }
                    if(tstring.trim().equals("$(FIRE_SRC_RPT_END)")) break;
                    if(!redirect) outputVector.addElement(tstring);
                    if(redirect) sourceRepeatTemplate.addElement(tstring);
                }

                for(int i=0;i<dispLanguages.length;i++) {
                    
                    // Get language suffix with pattern name
                    String sourceFile = replacedPatternImage + "." + languagesExtensions[i];
                    String sourceFilePath = sourcesDir + languagesExtensions[i] + "/" + goal + "/" + getProperName(sourceFile);

                    // Skip including the source repeat template if source does not exist
                    if(SRC_INDEX[i] == -1)  continue;

                    for(String srct: sourceRepeatTemplate) {

                        srct = srct.replaceAll("$(FIRE_LANG_ID)", languagesExtensions[i]);
                        srct = srct.replaceAll("$(FIRE_LANG_NAME)", dispLanguages[i]);
                        srct = srct.replaceAll("$(FIRE_SRC_NAME)", sourceFile);
                        srct = srct.replaceAll("$(FIRE_LANG_GH_LINK)", (ghURLprefix + ghLangTags[i] + "-PatternHouse/blob/main/" + capGoal + " Patterns/" + sourceFile));

                        outputVector.addElement(srct);

                        if(srct.trim().equals("$(FIRE_SRC)")) {
                            for(String src:get(SRC_INDEX[i])) outputVector.addElement(src);
                        } else continue;
                    }
                }

                redirect = false;
                for(String tstring:get(P_TEMPLATE_INDEX)) {
                    if(tstring.trim().equals("$(FIRE_SRC_RPT_END)")) redirect = true;
                    if(redirect) outputVector.addElement(tstring);
                }

                /* Replace All Fire Variables */
                Vector<String> fOutput = new Vector<>(1,1);
                for(String out:outputVector) {
                    for(int i=0;i<fireVariableName.size();i++) out = out.replaceAll(fireVariableName.elementAt(i), fireVariableValue.elementAt(i));
                    fOutput.addElement(out);
                }

                // Write the Generated file for the pattern
                String genGoalDir = capGoal + " Patterns";
                mkdir(outputDir+genGoalDir+"/");
                write(outputDir+genGoalDir+"/"+genPatternName+outputExtension, fOutput);
            }
        }
    }
}