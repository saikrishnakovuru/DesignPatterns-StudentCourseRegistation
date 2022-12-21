# CSX42: Assignment 1

## Name: saikrishna kovuru

---

---

---

## Instruction to clean:

####Command: ant -buildfile studentCoursesBackup/src/build.xml clean

Description: cleans up all the .class files that were generated when
compiled.

---

## Instruction to compile:

####Command: ant -buildfile studentCoursesBackup/src/build.xml all

Description: Compiles and generates .class files inside the BUILD folder.

---

## Instruction to run:

ant -buildfile studentCoursesBackup/src/build.xml run -Darg0=coursePrefs.txt -Darg1=courseInfo.txt -Darg2=regResults.txt -Darg3=regConflicts.txt -Darg4=errorLog.txt

please follow the path and names as mentioned int the above command, as I have name check in main class to pass before passing argumets to respective classes.

---

## Description:

Please ignore the name of first commit as I'm new and don't know how to use GitHub.
I committed 90% of the logic and all the necessary classes for the project.
In further commits I just modified the simple logics, added comments and removed the redundant code while going throught codes.
Done the programming in my best object oriented way, made use of interfaces through out the application.

Application execution starts for the Driver code,
Driver:
we have few checks in the main statemt and those are made to check the inputs passed from the command line, if the inputs passed are less than 5 we throw the error and also if any of the file mismatch occurs by throwing the error of input mismatch, also if every things is passed as expected and if teh info files are empty then we have to again throught the error saying info files are empty with no data.
We pass the args[4] --> which is errorLog.txt to and args[3]--> args[4] to CourseAllocatorImpl
and args[4] --> which is errorLog.txt to file processor along args[2] --> regResults to FileDisplayInterface and StdoutDisplayInterface.

then at last driver contructer called by passing all the necessary arguments to the Dirver constructor initializing the needed files to make them global and doStuff method starts.

fileProcessorI.readStudentCoursePreferences:
calling readStudentCoursePreferences through the interface where we parse the data of studentCoursePreferences and return them as an array list back to the driver class and adding those details into a studentPreferencesList;

fileProcessorI.readCourseInfo:
calling readCourseInfo through fileProcessorI interface where er parse the courseInfoDetails and return as an array list back to the dircer and adding those to the courseList.

courseAllocator:
Here the main logc exist, inorder to compare the results we have to pass both studentPreferencesList and courseList to the courseAllocator  
There we used setter injection to set the values of studentsPrferenceslist and coureList globally,
Here we iterated the courseList to log the error if there is any.
first for loop is to compare every preferedCourse of a student with the coursePrefs one by one, when ther is any time clash between the courses those are recorded as conflicts and has been sent to regConflicts file,
and a string[3] has been taken so that each student should only be given 3 courses, as soon as an course has been assigned the counter incremetns and goes on upto 3 , so that the student has to be given atmost of 3 courses.
Erros were been logged if there exists ant erros in studentPrefs list
If an error exists flag will be set to false and if the flag is false we will not allow the courseAllocator to run the ligic as we have errors in the input.
Satisfaction rating has also been calsulated.
I also checked with the 500 student input and few students were been assigned with 2 courses and few of them were been given a single course based on the course availability because as soon as the course has been assigned the course count will be decremetned by 1.

writeResultsToOutputFile:
later alotting the courses those alotted courses and satisfaction rating will be passed to writeResultsToOutputFile to print in regResults file.

displayResultsInConsole:
same as the above step alotted courses and satisfaction rating will be passed to writeResultsToOutputFile to print on console.

If there are no errors in any of the input files with well formatted data we will see the output in both console and in outputFile else those errors will be posted in errorLog file.

---

## Sources

(regex in the CourseAllocatorImpl logErrorsInCourseInfoFileInput in checking for the int value) --> stackOverFlow
Using delimeter in "String.join(",", coursesAssigned)" in CourseAllocatorImpl --> stackOverFLow.

### Academic Honesty statement:

---

"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating I will have to sign an
official form that I have cheated and that this form will be stored in
my official university record. I also understand that I will receive a
grade of 0 for the involved assignment for my first offense and that I
will receive a grade of F for the course for any additional
offense.""

Date: 09/29/2022.
