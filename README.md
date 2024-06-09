
# A simple but highly useful work time reporter for IT projects

This is a proof of concept for a reporter tool that allows IT professionals to efficiently track and report their work hours on various projects. Its straightforward interface help managers to generate detailed reports and ensuring accurate and transparent project management.

## Prerequisites
- java,
- terminal,
- excel,
- pdf reader,

## Generate report
Generates a report of time spent each month in a chosen format.

Command:
```
java -jar reporter.jar [path to file] [type] [output]
```
example path to file: _C://report/2012/01_

Available report types: 1, 2, 3
- 1 => monthly report
- 2 => people report
- 3 => tasks report

Available output types: console, excel, pdf.
By default, the report is printed in the console.

TODO Add an example images of an example report in console

*Examples*
```
java -jar reporter.jar C://report/2012 1 pdf
```
Generate report form folder C://report/2012 of type 1 (personal) and pdf output


## Types of reports
### Monthly report
### Personal report
### Tasks report

