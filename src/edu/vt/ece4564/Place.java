package edu.vt.ece4564;

public class Place {
	
		// Create a structure that is passed between the background network and front tasks
		String name;
		String vicinity;
		String reference;
		String formatted_phone_number;
		
	    // Default Constructor
		public Place() {
	        this.name = "";
	        this.vicinity = "";
	        this.reference = "";
	        this.formatted_phone_number = "";
		}
		
	    // Initial Constructor
		public Place(String a, String b, String c, String d) {
	        this.name = a;
	        this.vicinity = b;
	        this.reference = c;
	        this.formatted_phone_number = c;
		}
		
		// Set Name
		public void setName(String a)
		{
			
			this.name = a;
			
		}
		
		// Set Vicinity
		public void setVicinity(String b)
		{
			
			this.vicinity = b;
			
		}
		
		// Set Reference
		public void setReference(String c)
		{
			
			this.reference = c;
			
		}
		
		// Set Phone Number
		public void setPhoneNumber(String d)
		{
			
			this.formatted_phone_number = d;
			
		}

}
