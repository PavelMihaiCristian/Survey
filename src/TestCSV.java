import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestCSV
{
   public static boolean verifyAnswer(int index, String Qanswer)
         throws FileNotFoundException
   {
      Scanner scanner = new Scanner(
            new File(
                  "C:/Users/Bashar/Desktop/ICT Engineering/Fourth Semester/SEP4/SEP4D/Originaldata.csv"));
      // scanner.useDelimiter("\",\"");
      scanner.useDelimiter(",");
      while (scanner.hasNextLine())
      {
         String[] response = scanner.nextLine().split(",\"");
         if ((response[index].replace("\"", "")).equals(Qanswer))
         {
            return true;
         }
      }
      return false;
   }

   public static boolean verifyQuestion(int index, String Question)
         throws FileNotFoundException
   {
      Scanner scanner = new Scanner(
            new File(
                  "C:/Users/Bashar/Desktop/ICT Engineering/Fourth Semester/SEP4/SEP4D/Originaldata.csv"));
      // scanner.useDelimiter("\",\"");

      scanner.useDelimiter(",");
      String[] questions = scanner.nextLine().split(",\"");

      if (index + 1 < questions.length)
      {
         int indexofNext = index + 1;
         //You can also check if it does not split .length means the questions is valid
         String[] questionParts = questions[indexofNext].split(":");

         if (questionParts.length == 2){
            questionParts[1].replace("\"", "");
         System.out.println("///// " + "#" +  questionParts[1].replace("\"", "") + "#" + "#"
               + Question + "#");
         if (( questionParts[1].replace("\"", "")).equals(Question))
         {
            return true;
         }
         }
      }
      return false;
   }

   public static void main(String[] args) throws FileNotFoundException
   {
      Scanner scanner = new Scanner(
            new File(
                  "C:/Users/Bashar/Desktop/ICT Engineering/Fourth Semester/SEP4/SEP4D/Originaldata.csv"));
      // scanner.useDelimiter("\",\"");
      scanner.useDelimiter(",");
      String[] questions = scanner.nextLine().split(",\"");
      String[] answer = scanner.nextLine().split(",\"");
      System.out.println("Questions " + questions.length + " Answers "
            + answer.length);
      String[] questionParts;
      for (int i = 0; i < questions.length; i++)
      {
         System.out.println("Q/A (" + i + ")");
         System.out.println("Question: " + questions[i].replace("\"", ""));
         if (questions[i].contains(":"))
         {
            questionParts = questions[i].split(":");
            System.out.println("This is the question: "
                  + questionParts[1].replace("\"", ""));
            System.out.println("The Answer of Question: #" + questionParts[0]
                  + "#");
            System.out.println("IF THE ANSWER IN QUESTION EQUALS THE ANSWER: "
                  + questionParts[0].equals(answer[i].replace("\"", "")));
            System.out.println("Someone answered this Question:  "
                  + TestCSV.verifyAnswer(i, questionParts[0]));
            System.out
                  .println("The question is verified with the one after it: "
                        + TestCSV.verifyQuestion(i,
                              questionParts[1].replace("\"", "")));
            // Go through all answers and check if someone chose it
         }
         System.out.println("Answer: #" + answer[i].replace("\"", "") + "#");

         System.out.println("------------------");
      }

      // while(scanner.hasNext()){
      // //System.out.print(scanner.next()+"|");
      // }
      scanner.close();
   }

}// there are some questions they have choices like good bad but the answer is 0
 // , 1
//System.out.println("IF THE ANSWER IN QUESTION EQUALS THE ANSWER: "
//      + questionParts[0].equals(answer[i].replace("\"", "")));
//System.out.println("Someone answered this Question:  "
//      + TestCSV.verifyAnswer(i, questionParts[0]));
//System.out
//      .println("The question is verified with the one after it: "
//            + TestCSV.verifyQuestion(i,
//                  questionParts[1].replace("\"", "")));
//// Go through all answers and check if someone chose it
//questions[i] = questionParts[1].replace("\"", "");

//System.out.println("///// " + "#" +  questionParts[1].replace("\"", "") + "#" + "#"
//      + Question + "#");


// public ArrayList<String[]> getRespondantsWithAnswers() throws
// FileNotFoundException{
// ArrayList<String[]> respondants = new ArrayList<String[]>();
// Scanner scanner = getNewScanner();
// scanner.nextLine();
// while (scanner.hasNextLine())
// {
// String[] responses = scanner.nextLine().split(",\"");
// for(int i=0;i<responses.length;i++){
// responses[i] = responses[i].replace("\"", "");
// }
// respondants.add(responses);
// }
// return respondants;
// }
