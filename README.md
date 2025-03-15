# CompanyProject
Classroom Collectibles

HOW TO SET UP PROGRAM:
1. Make sure Java JDK version is 17 or higher 
2. Download javaFX: https://gluonhq.com/
3. Extract downloaded file into a known directory in IntelliJ
4. File (upper left hand corner) > Project structure > Libraries > Java (select ‘+’) > Click Apply > Click OK > Close the window
5. Select the lib file in the SDK for the file path
6. Configure JVM to include JavaFX values
7. Go to top of screen in IntelliJ > Current File > Edit configurations
8. Create a new configuration called ‘blindBoxGame’ > Modify options > Add VM option
      In the VM option box type:

          --module-path “File path to lib folder in the SDK” --add-modules javafx.controls,javafx.media
10. When running the program, select the blindBoxGame configuration
