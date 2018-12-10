# CS140-Final-Project
Binghamton University


Full Assembler coded in Java (GUI Included). Runs pasm and pexe files.


Project was originally inspired by a project called Pippin from pp.210-214 of "The Analytical Engine, An Introduction to Computer Science Using the Internet" by Rick Decker and Stuart Hirshfield (B Publishing Company, 1998). However, many extensions have been made.


### Running and Testing

You can start running the program through the `GUIMediator` class in the `project` package and testing the correct and incorrect programs.

The files such as `100rt.pexe` are supposed to run with a run-time exception and should pop up a DialogMessage.

### Here are the errors reported by our program. You may find some other errors or skip some and you may be giving different messages.

```
03e.pasm has one or more errors
Error on line 32: illegal mnemonic

04e.pasm has one or more errors
Error on line 34: data address is not a hex number

05e.pasm has one or more errors
Error on line 15. Illegal blank line in the source file
Error on line 21. Illegal blank line in the source file

06e.pasm has one or more errors
Error on line 15. Illegal blank line in the source file
Error on line 21. Line starts with illegal white space

07e.pasm has one or more errors
Error on line 15. Line starts with illegal white space
Error on line 20. Illegal blank line in the source file

08e.pasm has one or more errors
Error on line 15. Line starts with illegal white space
Error on line 20. Illegal blank line in the source file

09e.pasm has one or more errors
Error on line 33. Illegal blank line in the source file

10e.pasm has one or more errors
Error on line 19: data value is not a hex number
Error on line 20: data address is not a hex number
Error on line 21: data value is not a hex number
Error on line 22: data value is not a hex number
Error on line 23: data address is not a hex number
Error on line 24: data value is not a hex number
Error on line 25: data value is not a hex number
Error on line 26: data address is not a hex number
Error on line 27: data format does not consist of two numbers
Error on line 28: data address is not a hex number
Error on line 29: data address is not a hex number
Error on line 30: data format does not consist of two numbers
Error on line 31: data format does not consist of two numbers
Error on line 34. Illegal blank line in the source file

11e.pasm has one or more errors
Error on line 30: illegal mnemonic
Error on line 31: illegal mnemonic
Error on line 32: illegal mnemonic
Error on line 33: illegal mnemonic
Error on line 35: illegal mnemonic

12e.pasm has one or more errors
Error on line 9: mnemonic must be upper case
Error on line 30: illegal mnemonic
Error on line 34. Illegal blank line in the source file

13e.pasm has one or more errors
Error on line 26: this mnemonic cannot take arguments
Error on line 30: illegal mnemonic

14e.pasm has one or more errors
Error on line 30: illegal mnemonic

15e.pasm has one or more errors
Error on line 8: mnemonic must be upper case
Error on line 27: instruction does not allow immediate addressing

16e.pasm has one or more errors
Error on line 8: illegal mnemonic
Error on line 29: this mnemonic cannot take arguments

17e.pasm has one or more errors
Error on line 5: this mnemonic has too many arguments: E2 1
Error on line 8: this mnemonic is missing an argument
Error on line 19. Illegal blank line in the source file
Error on line 26. Line starts with illegal white space
Error on line 30: this mnemonic cannot take arguments
Error on line 31. Line starts with illegal white space
Error on line 34. Illegal blank line in the source file

18e.pasm has one or more errors
Error on line 5: this mnemonic has too many arguments: # E2
Error on line 28: instruction does not allow immediate addressing

19e.pasm has one or more errors
Error on line 11: this mnemonic has too many arguments: 0 12
Error on line 30. Line starts with illegal white space

20e.pasm has one or more errors
Error on line 10: argument is not a hex number: -1G

21e.pasm has one or more errors
Error on line 32: data address is not a hex number

22e.pasm has one or more errors
Error on line 32: data value is not a hex number

23e.pasm has one or more errors
Error on line 32: data value is not a hex number

24e.pasm has one or more errors
Error on line 12: argument is not a hex number: 100000000
Error on line 31: data format does not consist of two numbers
Error on line 32: data value is not a hex number

25e.pasm has one or more errors
Error on line 33: data value is not a hex number
```

### Here are messages from running the "10?rt" files

```
Illegal access to data from line 11
Exception message: Illegal access to data memory, index -1

Divide by zero from line 10
Exception message: Divide by Zero

Illegal access to code from line -1
Exception message: Illegal access outside of executing code

Divide by zero from line 16
Exception message: Divide by Zero

Divide by zero from line 16
Exception message: Divide by Zero
```




