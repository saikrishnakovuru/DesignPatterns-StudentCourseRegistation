# DesignPatterns-StudentCourseRegistation



## Instruction to compile:

####Command: ant -buildfile studentCoursesBackup/src/build.xml all

Description: Compiles and generates .class files inside the BUILD folder.

---

## Instruction to run:

ant -buildfile studentCoursesBackup/src/build.xml run -Darg0=coursePrefs.txt -Darg1=courseInfo.txt -Darg2=regResults.txt -Darg3=regConflicts.txt -Darg4=errorLog.txt

Need to add boundary conditions which are
1) What if there are no input files and data in the input files are empty and so forth.
