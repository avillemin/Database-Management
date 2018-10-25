import csv
import random

myFile=open('data.csv', 'w', newline='')
data=[[random.randint(0,99999999), random.randint(0,999999)] for i in range(500000)]

with myFile:
    writer=csv.writer(myFile)
    writer.writerows(data)