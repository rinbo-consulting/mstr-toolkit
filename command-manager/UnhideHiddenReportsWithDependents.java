ResultSet reports = executeCapture("LIST ALL REPORTS FOR PROJECT \""+projectName+"\";");

int totalReports = reports.getRowCount();

int reportCounter = 0;
int hiddenCounter = 0;
int unHiddenCounter = 0;

reports.moveFirst();

while (!reports.isEof()) {
    reportCounter++;
    Boolean isHidden = (Boolean)reports.getResultCell(HIDDEN).getValue();
    String folder = reports.getResultCell(FOLDER).getValueString();
    String name = reports.getResultCell(NAME).getValueString();
    printOut("Scanning report " + reportCounter + " of " + totalReports);
    if (isHidden) {
        hiddenCounter++;
        printOut ("Well, " + name + " is hidden...");
        ResultSet dependents = executeCapture("LIST ALL DEPENDENTS FOR REPORT \""+name+"\" IN FOLDER \""+folder+"\" IN PROJECT \""+projectName+"\";");
        if (dependents.getRowCount() > 0) {
            unHiddenCounter++;
            printOut("...and it has dependents. Unhiding.");
            execute("ALTER REPORT \""+name+"\" IN FOLDER \""+folder+"\" HIDDEN FALSE FOR PROJECT \""+projectName+"\";");
        }
    }
    reports.moveNext();
}
printOut("Reports scanned: " + reportCounter);
printOut("Reports hidden: " + hiddenCounter);
printOut("Reports unhidden: " + unHiddenCounter);