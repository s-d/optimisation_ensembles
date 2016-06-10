import os
import csv
from openpyxl import Workbook

saveDir = 'Excel'

if not os.path.exists(saveDir):
    print('Creating new directory for converted files.')
    os.makedirs(saveDir)

for csvFileName in os.listdir(os.getcwd()):
    if not csvFileName.endswith('.csv'):
        continue
    print('Converting ' + csvFileName)
    csvRows = []
    csvFileObj = open(csvFileName)
    readerObj = csv.reader(csvFileObj)
    for row in readerObj:
        csvRows.append(row)
    csvFileObj.close()

    wb = Workbook(guess_types=True)
    savePath = saveDir + '\\' + os.path.splitext(csvFileName)[0] + '.xlsx'
    ws1 = wb.active
    ws1.title = 'Sheet1'

    for i in csvRows:
        ws1.append(i)

    ws1['J1'] = 'true fitness'
    for i in range(2, ws1.max_row + 1):
        if ws1['G' + str(i)].value < ws1['E' + str(i)].value:
            ws1['J' + str(i)] = ws1['G' + str(i)].value
        else:
            ws1['J' + str(i)] = ws1['E' + str(i)].value

    wb.save(filename=savePath)

print('Complete.')
