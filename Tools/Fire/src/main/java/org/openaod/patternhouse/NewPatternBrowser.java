package org.openaod.patternhouse;

import org.openaod.patternhouse.util.FireExtensions;

import java.util.Vector;
import java.util.Collections;
import java.time.LocalDate;

public class NewPatternBrowser extends FireExtensions {

    /* Fire Debugging Variables */
    private static final String fireBuildID = "openAOD FIRE v1.2 Release Candidate 3";
    private static final String fireVersionString = "openaod-fire-v1.2rc3";
    private static final String fireDateString = "" + LocalDate.now();

    /* Pattern images URL prefix */
    private static final String imgURLprefix = "../../patterns/";

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

    public static void build() {

        /* Checks to ensure that template is found */
        if(!exists(templatesDir+pSourceTemplatePath)) {
            System.out.println(" CHECK [FAIL]: Primary source template not found.");
            System.out.println(" CHECK [FAIL]: Searched file : "+(templatesDir+pSourceTemplatePath));
            System.exit(101);
        }

        System.out.println(" CHECK [INFO] : PASS");

        // Ensure build directory is present and ready
        mkdir(outputDir);

        // Load the template source into VCache memory
        int P_TEMPLATE_INDEX = read(templatesDir+pSourceTemplatePath);

        // Loop through all the goals
        for(String goal:goals) {

            System.out.println(" FIRE [INFO] : Reached Goal \""+goal+"\"");

            // Get all possible pattern images for the goal
            int GOAL_PATTERNS_INDEX = listFiles(inputImagesDir+goal);
            
            // Loop through all the possible pattern images for the goal
            for(String patternImage:get(GOAL_PATTERNS_INDEX)) {

                System.out.println(" FIRE [INFO] : Looking for \""+patternImage+"\" under goal \""+goal+"\"");

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
                System.out.println(" FIRE [INFO] : Reached Goal \""+capGoal+"\"");
                System.out.println(" FIRE [INFO] : Reached genPatternName \""+genPatternName+"\"");

                /* VCache Vector of Source Files */
                int[] SRC_INDEX = new int[dispLanguages.length];

                System.out.println(" FIRE [INFO] : Starting preliminary indexing");
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

                System.out.println(" FIRE [INFO] : Calculating preliminary Fire variables");
                /* Calculate Fire Variables */

                String imgURL = imgURLprefix + goal + "/" + getProperName(patternImage);

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

                System.out.println(" FIRE [INFO] : Mapping preliminary Fire variables");
                /* Map Fire Variables */

                /* Fire Variable <=> Name Map */
                Vector<String> fireVariableName = new Vector<>(1,1);
                Vector<String> fireVariableValue = new Vector<>(1,1);

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
                    String upLang = languagesExtensions[i].toUpperCase();
                    fireVariableName.addElement("$(FIRE_"+ upLang +"_LANG_SUP)");
                    fireVariableValue.addElement(langStatus[i]);
                }

                // Fire Previous Pattern Link and Status
                fireVariableName.addElement("$(FIRE_PREV_PNLNK)");
                if(!prevGenPatternName.equals("#")) fireVariableValue.addElement(prevGenPatternName+outputExtension);
                else fireVariableValue.addElement(prevGenPatternName);
                fireVariableName.addElement("$(FIRE_PREV_STATUS)");
                fireVariableValue.addElement(prevPStatus);

                // Fire Next Pattern Link and Status
                fireVariableName.addElement("$(FIRE_NEXT_PNLNK)");
                if(!nextGenPatternName.equals("#")) fireVariableValue.addElement(nextGenPatternName+outputExtension);
                else fireVariableValue.addElement(nextGenPatternName);
                fireVariableName.addElement("$(FIRE_NEXT_STATUS)");
                fireVariableValue.addElement(nextPStatus);

                // Fire ending note status
                fireVariableName.addElement("$(FIRE_NTDSP_FLG)");
                fireVariableValue.addElement(noteFlag);

                // Fire Unsupported Langauages
                fireVariableName.addElement("$(FIRE_UNSUP_LANG)");
                fireVariableValue.addElement(unsupLang);
                
                // Fire Debug variables
                fireVariableName.addElement("$(FIRE_BUILD_ID)");
                fireVariableValue.addElement(fireBuildID);

                fireVariableName.addElement("$(FIRE_BUILD_DATE)");
                fireVariableValue.addElement(fireDateString);

                fireVariableName.addElement("$(FIRE_VERSION)");
                fireVariableValue.addElement(fireVersionString);

                /* Prepare the template */
                System.out.println(" FIRE [INFO] : Preparing preliminary output vector");
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

                    System.out.println(" FIRE [INFO] : Processing source repeat template vector for langauage \""+dispLanguages[i]+"\"");

                    // Get language suffix with pattern name
                    String sourceFile = replacedPatternImage + "." + languagesExtensions[i];
                    String sourceFilePath = sourcesDir + languagesExtensions[i] + "/" + goal + "/" + getProperName(sourceFile);

                    // Skip including the source repeat template if source does not exist
                    if(SRC_INDEX[i] == -1)  continue;

                    for(String srct: sourceRepeatTemplate) {

                        srct = srct.replace("$(FIRE_LANG_ID)", languagesExtensions[i]);
                        srct = srct.replace("$(FIRE_LANG_NAME)", dispLanguages[i]);
                        srct = srct.replace("$(FIRE_SRC_NAME)", getProperName(sourceFile));
                        srct = srct.replace("$(FIRE_LANG_GH_LINK)", (ghURLprefix + ghLangTags[i] + "-PatternHouse/blob/main/" + capGoal + " Patterns/" + getProperName(sourceFile)));
                        srct = srct.replace("$(FIRE_LANG_DW_LINK)", (ghURLprefix + ghLangTags[i] + "-PatternHouse/raw/main/" + capGoal + " Patterns/" + getProperName(sourceFile)));

                        if(srct.trim().equals("$(FIRE_SRC)")) {
                            System.out.println(" FIRE [INFO] : Processing source code");
                            for(String src:get(SRC_INDEX[i])) {
                                outputVector.addElement(process(src));
                            }
                        } else {
                            outputVector.addElement(srct);
                        }
                    }
                }

                redirect = false;
                for(String tstring:get(P_TEMPLATE_INDEX)) {
                    if(tstring.trim().equals("$(FIRE_SRC_RPT_END)")) { redirect = true; continue; }
                    if(redirect) outputVector.addElement(tstring);
                }

                /* Replace All Fire Variables */
                Vector<String> fOutput = new Vector<>(1,1);
                System.out.println(" FIRE [INFO] : Processing final output vector ");
                for(String out:outputVector) {
                    for(int i=0;i<fireVariableName.size();i++) out = out.replace(fireVariableName.elementAt(i), fireVariableValue.elementAt(i));
                    fOutput.addElement(out);
                }

                // Write the Generated file for the pattern
                String genGoalDir = goal;
                mkdir(outputDir+genGoalDir+"/");
                System.out.println(" FIRE [INFO] : Writing final output vector ");
                write(outputDir+genGoalDir+"/"+genPatternName+outputExtension, fOutput);
            }
        }

        buildPortalDasboard();
    }

    /* Primary Portal Webpage Template */
    private static final String pPortalTemplatePath = "PortalDisplay/Template.html";

    /* Primary Portal Webpage Target */
    private static final String dashboardWebpagePage = "./Portal/portal/index.html";

    /* Primary Portal Webpage Image Prefix */
    private static final String pImagePrexfix = "../patterns/";

    private static void buildPortalDasboard() {
        System.out.println(" FIRE [INFO] : Building Portal frontend ... ");

        /* Checks to ensure that template is found */
        if(!exists(templatesDir+pPortalTemplatePath)) {
            System.out.println(" CHECK [FAIL]: Primary source template not found.");
            System.out.println(" CHECK [FAIL]: Searched file : "+(templatesDir+pPortalTemplatePath));
            System.exit(101);
        }

        System.out.println(" CHECK [INFO] : PASS");

        int P_TEMPLATE_INDEX = read(templatesDir+pPortalTemplatePath);
        
        String pnrptt = "";
        String lnrptt = "";

        boolean redirect = false;
        Vector<String> cardTemplate = new Vector<>(1,1);

        /* Process the template */
        for(String line:get(P_TEMPLATE_INDEX)) {
            String templateLine = line.trim();
            if(templateLine.equals("$(FIRE_CARDTE)")) { redirect = false; continue; }
            if(redirect) cardTemplate.addElement(templateLine);
            else {
                if(templateLine.equals("$(FIRE_CARDTS)")) { redirect = true; continue; }
                if(templateLine.startsWith("$(FIRE_PN_RPTT)")) pnrptt = templateLine.replace("$(FIRE_PN_RPTT)", "").trim();
                if(templateLine.startsWith("$(FIRE_LN_RPTT)")) lnrptt = templateLine.replace("$(FIRE_LN_RPTT)", "").trim();
            }
        }

        Vector<String> outputVector = new Vector<>(1,1);
        
        redirect = false;

        for(String line:get(P_TEMPLATE_INDEX)) {
            if(redirect) {
                if(line.trim().startsWith("$(FIRE_CARDTE)")) redirect = false;
                continue;
            }
            if(line.trim().startsWith("$(FIRE_CARDTS)")) {
                redirect = true;
                continue;
            }
            if(line.trim().startsWith("$(FIRE_PN_RPTT)")) {
                for(String goal:goals) outputVector.addElement(pnrptt.replace("$(FIRE_PNNAME)", goal));
            } else if(line.trim().startsWith("$(FIRE_LN_RPTT)")) {
                for(String lang:dispLanguages) outputVector.addElement(lnrptt.replace("$(FIRE_LNNAME)", lang));
            } else if(line.trim().equals("$(FIRE_DASH_RPT)")) {
                
                for(String goal:goals) {
                    // Get all possible pattern images for the goal
                    int GOAL_PATTERNS_INDEX = listFiles(inputImagesDir+goal);
                    
                    Vector<String> files = get(GOAL_PATTERNS_INDEX);
                    Collections.sort(files);

                    // Loop through all the possible pattern images for the goal
                    for(String patternImage:files) {
                        

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
                        System.out.println(" FIRE [INFO] : Reached Goal \""+capGoal+"\"");
                        System.out.println(" FIRE [INFO] : Reached genPatternName \""+genPatternName+"\"");

                        /* VCache Vector of Source Files */
                        int[] SRC_INDEX = new int[dispLanguages.length];

                        System.out.println(" FIRE [INFO] : Starting preliminary indexing");
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

                        String patternType = goal;
                        String patternGenPN = genPatternName;
                        String labelrpt = "";
                        String imgURL = pImagePrexfix + goal + "/" + getProperName(patternImage);
                        String codeLink = goal+"/"+genPatternName+outputExtension;
                        
                        for(String template:cardTemplate) {
                            String out = template.trim();
                            if(out.startsWith("$(FIRE_LN_RPTT)")) {
                                out = out.replace("$(FIRE_LN_RPTT)", "");
                                for(int i=0;i<dispLanguages.length;i++) {
                                    String lns = "";
                                    if(SRC_INDEX[i] != -1) lns = "-active";
                                    String lout = out.replace("$(FIRE_LNS)", lns).replace("$(FIRE_LNNAME)", dispLanguages[i]);
                                    outputVector.addElement(lout);
                                }
                            } else {
                                out = out.replace("$(FIRE_PNT)", patternType);
                                out = out.replace("$(FIRE_GNPN)", patternGenPN);
                                out = out.replace("$(FIRE_IMG_LINK)", imgURL);
                                out = out.replace("$(FIRE_CODE_LINK)", codeLink); 
                                outputVector.addElement(out);
                            }
                        }

                    }

                }

            } else {
                outputVector.addElement(line);
            }
        }

        System.out.println("Writing dashboard output vector ... ");
        write(dashboardWebpagePage, outputVector);
    }


    /* Fixes problems with rendering in case of tags within the code
     * See https://stackoverflow.com/questions/2820453/how-to-display-raw-html-code-on-an-html-page
     **/
    public static String process(String st) {
        String out = "";
        for(int i=0;i<st.length();i++) {
            char ch = st.charAt(i);
            if(ch == '&') {
                out += "&amp;";
            } else if(ch == '<') {
                out += "&lt;";
            } else if (ch == '>') {
                out += "&gt;";
            } else {
                out += ch;
            }
        }
        return out;
    }
}
