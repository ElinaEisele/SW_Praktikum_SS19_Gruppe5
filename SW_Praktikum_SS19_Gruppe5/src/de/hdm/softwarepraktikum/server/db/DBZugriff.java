package de.hdm.softwarepraktikum.server.db;


public class DBZugriff {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

			
			Float pn = ListitemMapper.listitemMapper().getAmountOf(1);
			
			System.out.println(pn);
		

		
	}

}
