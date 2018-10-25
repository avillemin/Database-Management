import random

myFile=open('request.sql', 'w')

request0="INSERT INTO registers(student_id, course_id) VALUES ("

for i in range(500000):
	student_id=random.randint(0, 999999)
	course_id=random.randint(0, 999999)
	request = request0 +str(student_id)+' ,'+str(course_id)+') ; '
	myFile.write(request)
	
myFile.close()
