package etc;

public class DataProc 
{
	String recvData;
	
	public DataProc(String recvData) 
	{
		this.recvData = recvData;
		Print();
	}
	
	public void Print()
	{
		System.out.println("DataProc : " + recvData);
	}
}