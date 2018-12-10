package project;

public class Data {

	public static final int DATA_SIZE = 2048;
	int[] data = new int[DATA_SIZE];
	int changedIndex = -1;
	
	int getData(int index){
		if(index < 0 || index > DATA_SIZE) {
			throw new MemoryAccessException("Illegal access to data memory, index " + index);
		}
		return data[index];
	}
	
	int[] getData() {
		return data;
	}
	
	int getChangedIndex(){
		return changedIndex;
	}
	
	
	int setChangedIndex(int index){
		if(index < 0 || index > DATA_SIZE) {
			throw new MemoryAccessException("Illegal access to data memory, index " + index);
		}
		changedIndex = index;
		return changedIndex;
	}
	
	void setData(int index, int value){
		if(index < 0 || index > DATA_SIZE) {
			throw new MemoryAccessException("Illegal access to data memory, index " + index);
		}
		data[index] = value;
		changedIndex = index;
	}
	
	void clearData(int start, int end) {
		for(int i = start; i < end;i++) {
			data[i] = 0;
			changedIndex = -1;
		}
	}
	
	
}
