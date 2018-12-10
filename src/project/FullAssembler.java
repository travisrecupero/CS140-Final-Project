package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class FullAssembler extends Assembler {


	@Override
	public int assemble(String inputFileName, String outputFileName, TreeMap<Integer, String> errors) {
		if(errors == null) throw new IllegalArgumentException("Coding error: the error map is null");

	
		int retVal = 0;

		int lineNum = 0;

		boolean inCode = true;

		boolean blankLineFound = false;
		int firstBlankLineNum = 0;

		List<String> code = new ArrayList<>();
		List<String> data = new ArrayList<>();

		
		try (Scanner input = new Scanner(new File(inputFileName))) {
			while(input.hasNextLine()) {
				lineNum++;
				String line = input.nextLine();

				if(line.trim().length() == 0 && !blankLineFound){
					blankLineFound = true;
					firstBlankLineNum = lineNum;
				}
				else if(line.trim().length() > 0) {
					//look for illegal white space.
					//this occurs if line starts with a space or tab.
					if(line.charAt(0) == '\t' || line.charAt(0) == ' '){
						errors.put(lineNum + 1, "Error on line " +  lineNum + ": Line starts with an illegal whitespace.");
						retVal = lineNum + 1;
					}
					//if we have found a blankLine before this current non-blank line
					//then the blankLine found earlier is an Illegal Blank Line
					if(blankLineFound) {
						//report the error at the firstBlankLineNum, not the current lineNum.
						errors.put(firstBlankLineNum, "Error on line " +  firstBlankLineNum + ": Illegal black line in the source file.");
						retVal = lineNum + 1;
					}
					blankLineFound = false;
					//check if this non-blank line starts with the DATA delimeter 
					//we do the upper case to ensure we see DATA even if its not all uppercase
					if(line.trim().toUpperCase().contains("DATA")){
						if(inCode) {
							inCode = false;
						} else {
							errors.put(lineNum, "Error on line " +  lineNum + ": Duplicate DATA delimiter error");
							retVal = lineNum + 1;
						}

						if(!line.trim().contains("DATA")){
							errors.put(lineNum, "Error on line " +  lineNum + ": DATA delimiter must be all uppercase");
							retVal = lineNum + 1;
						}
					} 
				} 
				if(inCode) {
					code.add(line.trim());
				} else {
					data.add(line.trim());
				}
			} 
			

			//reset lineNum to zero, we are going to parse from the beginning of the file again.
			lineNum = 0;
			//now we find code specific errors
			for(String line : code){
				lineNum++;
				if(line.length() > 0){
					//we start by splitting the line of code into its parts
					//note that parts[0] is the MNEMONIC of the instruction, such as ADD
					//parts[1] (if it is present) will be the argument to the instruction

					//it is important to remember that some instructions will take
					//arguments, while some instructions do not take any arguments
					String[] parts = line.split("\\s+");

					//check that parts[0] is a valid mnemonic
					//if OPCODES.containsKey() returns true when you pass
					//in parts[0].toUpperCase(), then parts[0] is an valid mnemonic. 
					if(Model.OPCODES.containsKey(parts[0].toUpperCase())) {
						//check if the valid mnemonic is all uppercase
						if(!parts[0].equals(parts[0].toUpperCase())) {
							errors.put(lineNum, "Error on line " +  lineNum + ": Mnemoic is not uppercase");
							retVal = lineNum + 1;
						}
						

						//next, we check if the mnemonic takes no arguments 
						if(Model.NO_ARG_MNEMONICS.contains(parts[0])){
							//but now, if this instruction was given an argument
							//report an error saying the mnemonic cannot take arguments
							//you can check to see if an argument was provided by checking if 
							//parts.length is greater than 1
							if(parts.length > 1) {
								errors.put(lineNum, "Error on line " +  lineNum + ": this mnemonic cannot take arguments");
								retVal = lineNum + 1;
							}
						}
						//otherwise, we have an instruction that must take
						//exactly 1 argument
						else{
							//report if the mnemonic has too many arguments
							if(parts.length > 2){
								errors.put(lineNum, "Error on line " +  lineNum+ ": this mnemonic has too many arguments: " + line);
								retVal = lineNum + 1;
							}
							//check and report whether the 
							//mnemonic has too few arguments
							else if(parts.length < 2){
								errors.put(lineNum, "Error on line " +  lineNum + ": this mnemonic is missing an argument");
								retVal = lineNum + 1;
							}
							//otherwise, we need to check the mode provided is allowed
							//for the given instruction
							//and also need to check that the argument provided 
							//is a valid hexadecimal number
							else{
								//check for immediate mode
								if(parts[1].charAt(0) == '#'){
									//need to remove that special char so we have the argument
									parts[1] = parts[1].substring(1);
									if(Model.IND_MNEMONICS.contains(parts[0])) {
										errors.put(lineNum, "Error on line " + lineNum + 
												": instruction does not allow immediate addressing");
										retVal = lineNum + 1;
									}
								}
								//check for '@' for indirect mode.
								//all intructions can use this mode
								//however, we still need to remove that '@' char from 
								//parts[1], as we did similiarly in the '#' block
								else if(parts[1].charAt(0) == '@'){
									parts[1] = parts[1].substring(1);
								}
								//check for '&' for the special "null" mode of JUMP and JMPZ
								else if(parts[1].charAt(0) == '&'){
									parts[1] = parts[1].substring(1);
									if(!Model.JMP_MNEMONICS.contains(parts[0])) {
										errors.put(lineNum, "Error on line " + lineNum + 
												": instruction does not allow jump addressing");
										retVal = lineNum + 1;
									}
									//similar to the check in the '#' block above for IND_MNEMONICS,
									//if the mnemonic is not contained in Model.JMP_MNEMONICS
									//report an error saying the instruction doesn't allow jump addressing
								}

								//now, we check that the argument provided to the instruction is a valid hexadecimal number.
								try{
									//try to convert parts[1] to hexadecimal, base 16
									Integer.parseInt(parts[1], 16);
								} catch(NumberFormatException e) {
									errors.put(lineNum, "Error on line " + lineNum + 
											": argument is not a hex number: " + parts[1]);
									retVal = lineNum;
								}
							}
						}

					} /*this is the end of the valid mnemonic error checks*/

					//otherwise report an invalid mnemonic error
					else{
						errors.put(lineNum, "Error on line " +  lineNum + ": illegal mnemonic");
						retVal = lineNum + 1;
					}
				}/*end of line.length() > 0 block*/
			}/*end of code specific errors*/

			//now we find data specific errors
			for(String line : data){
				lineNum++;
				//we only want to look for errors on a non-blank line 
				//that is not a DATA delimeter
				if(line.length() > 0 && !line.toUpperCase().contains("DATA")){
					String[] parts = line.split("\\s+");
					//there are only three things you need to check here.
					//recall that a DATA entry should just be two hexidecimal numbers

					//if parts.length is not 2, report an error
					//saying data entry does not consist of two numbers.
					if(parts.length != 2){
						errors.put(lineNum, "Error on line " + lineNum + ": Data entry does not consist of two numbers");
						retVal = lineNum + 1;
					}	
					//othwerise we need to make sure parts[0] and parts[1] are hexidecimal
					else{
						//convert parts[0] to a hexadecimal number
						//if it fails, the error message is that the data's memory address in not a hex number
						try{
							//try to convert parts[1] to hexadecimal, base 16
							Integer.parseInt(parts[0], 16);
						} catch(NumberFormatException e) {
							errors.put(lineNum, "Error on line " + lineNum + 
									": data address is not a hex number: " + parts[1]);
							retVal = lineNum + 1;
						}
						//convert parts[1] to hex
						//if it fails, the error message is that the data's value is not a hex number
						try{
							//try to convert parts[1] to hexadecimal, base 16
							Integer.parseInt(parts[1], 16);
						} catch(NumberFormatException e) {
							errors.put(lineNum, "Error on line " + lineNum + 
									": data value is not a hex number: " + parts[1]);
							retVal = lineNum + 1;
						}
					}


				}
			}

		} /*end of massive try block where we did all the error checking*/ 
		catch (FileNotFoundException e) {
			errors.put(-1, "Unable to open the input file");
			retVal = -1;
		}

		//finally, if we found no errors, then we call the assemble method from our parent to actual assemble the pasm file
		//to an executable pexe file.
		if(errors.size() == 0) {
			super.assemble(inputFileName, outputFileName, errors);
		}
		return retVal;

	}

}
