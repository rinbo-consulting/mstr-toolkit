ResultSet metrics = executeCapture("LIST ALL METRICS FOR PROJECT \""+projectName+"\";");

int totalMetrics =metrics.getRowCount();

int metricCounter = 0;
int metricDeletedCounter = 0;

metrics.moveFirst();

while (!metrics.isEof()) {
    metricCounter++;
    String folder = metrics.getResultCell(PATH).getValueString();
    String name = metrics.getResultCell(NAME).getValueString();
    printOut("Scanning metric " + metricCounter + " of " + totalMetrics);
        ResultSet dependents = executeCapture("LIST ALL DEPENDENTS FOR METRIC \""+name+"\" IN FOLDER \""+folder+"\" IN PROJECT \""+projectName+"\";");
        if (dependents.getRowCount() == 0) {
            metricDeletedCounter++;
            printOut("...and it has no dependents. Gone.");
            execute("DELETE METRIC \""+name+"\" IN FOLDER \""+folder+"\" FROM PROJECT \""+projectName+"\";");
        } else {
        printOut("...but something's using it, so it lives.");
        }
    metrics.moveNext();
}
printOut("Metrics scanned: " + metricCounter);
printOut("Metrics deleted: " + metricDeletedCounter);

