import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGPTAPI {

   public static String chatGPT(String prompt) {
       String url = "https://api.openai.com/v1/chat/completions";
       String apiKey = "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"; // 여기에 고유 API 키 삽입, 만약 크레딧이 없다면 작동X
       String model = "gpt-3.5-turbo";
       try {
           URL obj = new URL(url);
           HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
           connection.setRequestMethod("POST");
           connection.setRequestProperty("Authorization", "Bearer " + apiKey);
           connection.setRequestProperty("Content-Type", "application/json; utf-8");
           connection.setDoOutput(true);

           // The request body
           String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
           connection.setDoOutput(true);
           OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
           writer.write(body);
           writer.flush();
           writer.close();
           
           
           // Response from ChatGPT
           BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

           String line;

           StringBuffer response = new StringBuffer();

           while ((line = br.readLine()) != null) {
               response.append(line);
           }
           br.close();

           // calls the method to extract the message.
           return extractMessageFromJSONResponse(response.toString());

       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }

   public static String extractMessageFromJSONResponse(String response) {
       int start = response.indexOf("content")+ 11;

       int end = response.indexOf("\"", start);

       return response.substring(start, end);

   }
   
   // 합치는 시점에서 제거, 답변을 받는 데에 15~20초 가량 소요.
   public static void main(String[] args) {

       System.out.println(chatGPT("강낭콩으로 시를 지어줘.?"));

   }
}