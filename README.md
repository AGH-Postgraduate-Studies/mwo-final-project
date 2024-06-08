
# A simple but highly useful work time reporter for IT projects

This is a proof of concept for a reporter tool that allows IT professionals to efficiently track and report their work hours on various projects. Its straightforward interface help managers to generate detailed reports and ensuring accurate and transparent project management.

## Prequisities
- terminal
- java
- excel

## Functionalities
### Generate projects report
Generates a report of time spent each month in a chosen format.

Command:
```
java -jar reporter.jar -project [path to file]
```
example path to file: _C://report/2012/01_
By default, the report is printed in the console.

TODO Add an example images of an example report in console

*Options*
```
--excel
```
Write the report into an Excel file at the destination ???
```
--pdf
```
Write the report into a PDF file at the destination ???
```
--chart
```
Generate chart file at the destination ???
### Generate personal report
Generates a report with hours spent by each developer in a given time span.
Command:
```
java -jar reporter.jar -person [path to file]
```
example path to file: _C://report/2012/01_
By default, the report is printed in the console.

TODO Add an example images of report images

*Options*
```
--excel
```
Write the report into an Excel file at the destination ???
```
--pdf
```
Write the report into a PDF file at the destination ???
```
--chart
```
### Generate tasks report
Generates a report with hours spent by each developer in a given time span.
Command:
```
java -jar reporter.jar -task [path to file]
```
example path to file: _C://report/2012/01_
By default, the report is printed in the console.

TODO Add an example images of report images

*Options*
```
--excel
```
Write the report into an Excel file at the destination ???
```
--pdf
```
Write the report into a PDF file at the destination ???
```
--chart
```
