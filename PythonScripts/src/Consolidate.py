import os
from openpyxl import Workbook
from openpyxl import load_workbook
import statistics

cwb = Workbook()
cws = cwb.active
cws.title = 'Sheet1'

headers = ['ensemble id', 'number of runs', 'median fitness', 'mode fitness', 'mean fitness', 'lowest fitness',
           'highest fitness', 'range', 'algorithms']

cws.append(headers)

rowCount = 2
for excelFileName in os.listdir(os.getcwd()):
    if not excelFileName.endswith('.xlsx'):
        continue

    print("Adding " + excelFileName + " to file.")
    wb = load_workbook(filename=excelFileName)
    ws = wb.active
    enId = ws['F2'].value
    runs = ws.max_row - 1

    fitness = []
    for i in range(2, ws.max_row):
        var = ws['J' + str(i)].value
        fitness.append(var)

    fitness.sort()

    cws['A' + str(rowCount)] = enId
    cws['B' + str(rowCount)] = runs
    cws['C' + str(rowCount)] = statistics.median(fitness)

    try:
        cws['D' + str(rowCount)] = statistics.mode(fitness)
    except statistics.StatisticsError:
        print('')
    cws['E' + str(rowCount)] = statistics.mean(fitness)
    cws['F' + str(rowCount)] = fitness[0]
    cws['G' + str(rowCount)] = fitness[-1]
    cws['H' + str(rowCount)] = fitness[-1] - fitness[0]
    cws['I' + str(rowCount)] = ws['I2'].value

    rowCount += 1
cwb.save(filename='ensembleConsolidation.xlsx')
