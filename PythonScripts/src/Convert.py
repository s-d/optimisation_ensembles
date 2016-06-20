# a script to convert HyFlex .csv files into .xlsx workbooks
import os
import csv
from openpyxl import Workbook

# name of directory for .xlsx files to be saved
saveDir = 'Excel'

# check if directory for converted files already exists
if not os.path.exists(saveDir):
    # create directory for converted files
    print('Creating new directory for converted files.')
    os.makedirs(saveDir)

# for each file in current directory
for csvFileName in os.listdir(os.getcwd()):
    # if not a .csv file, skip
    if not csvFileName.endswith('.csv'):
        continue

    print('Converting ' + csvFileName)
    csvRows = []
    # open .csv file
    csvFileObj = open(csvFileName)
    readerObj = csv.reader(csvFileObj)

    # for each row in the file
    for row in readerObj:
        # add row to list
        csvRows.append(row)
    csvFileObj.close()

    # create new workbook
    wb = Workbook(guess_types=True)
    savePath = saveDir + '\\' + os.path.splitext(csvFileName)[0] + '.xlsx'
    ws1 = wb.active
    ws1.title = 'Sheet1'

    # append .csv rows to workbook
    for i in csvRows:
        ws1.append(i)

    # calculate true fitness (may be redundant)
    ws1['J1'] = 'true fitness'
    for i in range(2, ws1.max_row + 1):
        if ws1['G' + str(i)].value < ws1['E' + str(i)].value:
            ws1['J' + str(i)] = ws1['G' + str(i)].value
        else:
            ws1['J' + str(i)] = ws1['E' + str(i)].value

    # save workbook
    wb.save(filename=savePath)

print('Complete.')
