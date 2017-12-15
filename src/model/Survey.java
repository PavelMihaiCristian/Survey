package model;

public class Survey
{

   private String title;
   private String description;
   private int year;
   
   
   public Survey(String title, String description, int year)
   {
      super();
      this.title = title;
      this.description = description;
      this.year = year;
   }
   public String getTitle()
   {
      return title;
   }
   public void setTitle(String title)
   {
      this.title = title;
   }
   public String getDescription()
   {
      return description;
   }
   public void setDescription(String description)
   {
      this.description = description;
   }
   public int getYear()
   {
      return year;
   }
   public void setYear(int year)
   {
      this.year = year;
   }
   
}
