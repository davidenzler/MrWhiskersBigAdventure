Log of Modifications to API outside of checkpoints
2:11:2023:: Screen dimensions are now FINAL, global variables at top of MAIN class. These can be modified to change dimensions.
    Allows reference to variables to dynamically scale tiling algorithms and anything else I may need later
2:15:2023:: Added in a group of static methods which will automatically update the art.txt file to reflect any changes to the
    ./Art directory and any subdirectories
2:15:2023:: The EZWrite class now has an overloaded constructor which allows for appending to files instead of writing over them.
    default behavior is set to write over. (behavior of EZWrite before overloading)