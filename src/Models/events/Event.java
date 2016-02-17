package Models.tasks;

public abstract class Event{

   private String title;
   private String description;
   
   Event(String title, String description){
	   this.setTitle(title);
	   this.setDescription(description) = description;
   }
   
   public String getTitle(){
	   return this.title;
   }
   
   public String getDescription(){
	   return this.description;
   }
   
   private setTitle(String title){
	   this.title = title;
   }
   
   private setDescription(String description){
	   this.description = description;
   }
   

}
