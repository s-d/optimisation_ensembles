import os
import csv
from openpyxl import Workbook

saveDir = 'excel'

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

    wb = Workbook()
    savePath = saveDir + '\\' + os.path.splitext(csvFileName)[0] + '.xlsx'
    ws1 = wb.active
    ws1.title = 'Sheet1'

    for i in csvRows:
        ws1.append(i)
    wb.save(filename=savePath)

input()
