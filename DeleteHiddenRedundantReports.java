// Copyright Paul Bailey 2013

ResultSet reports = executeCapture("LIST ALL REPORTS FOR PROJECT \"" + projectName + "\";");

int totalReports = reports.getRowCount();

int reportCounter = 0;
int hiddenCounter = 0;
int deletedCounter = 0;

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
        ResultSet dependents = executeCapture("LIST ALL DEPENDENTS FOR REPORT \"" + name + "\" IN FOLDER \"" + folder + "\" IN PROJECT \"" + projectName + "\";");
        if (dependents.getRowCount() == 0) {
            deletedCounter++;
            printOut("...and it has no dependents. Gone.");
            execute("DELETE REPORT \"" + name + "\" IN FOLDER \"" + folder + "\" FROM PROJECT \"" + projectName + "\";");
        } else {
        printOut("...but something's using it, so it lives.");
        }
    }
    reports.moveNext();
}
printOut("Reports scanned: " + reportCounter);
printOut("Reports hidden: " + hiddenCounter);
printOut("Reports deleted: " + deletedCounter);