

import java.io.File;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;

import io.restassured.RestAssured;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Paytm {

	 

	@Test
	public static void GetStatusCode()
	{

	RestAssured.baseURI = "https://apiproxy.paytm.com/v2/movies/upcoming";
	RequestSpecification httpRequest = RestAssured.given();
	Response response = httpRequest.get();
	int statusCode = response.getStatusCode();
	Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
	
	}


	@Test
	public static void Moviereleasedate()
	{

	RestAssured.baseURI = "https://apiproxy.paytm.com/v2/movies/upcoming";
	RequestSpecification httpRequest = RestAssured.given();
	Response response = httpRequest.get();
		
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
	LocalDateTime now = LocalDateTime.now();  			
		
	String json = response.asString();
	boolean flag = true;
	List<String> releaseDate = JsonPath.read(json, "$.upcomingMovieData[*].releaseDate");
	
	for (int i = 0; i < releaseDate.size()-1; i++) {
		
		if(releaseDate.get(i).compareTo(dtf.format(now)) < 0)
		{
			flag =false;
			break;
		}	
	}
	Assert.assertTrue(flag);
	
	}
	
	@Test
	public static void MoviePosterURL()
	{

	RestAssured.baseURI = "https://apiproxy.paytm.com/v2/movies/upcoming";
	RequestSpecification httpRequest = RestAssured.given();
	Response response = httpRequest.get();
	
	String json = response.asString();
	boolean flag = true;
	List<String> url = JsonPath.read(json, "$.upcomingMovieData[*].moviePosterUrl");
	
	for (int i = 0; i < url.size()-1; i++) {
		
		if(!url.get(i).contains(".jpg"))
		{
			flag =false;
			break;
		}	
	}
	Assert.assertTrue(flag);
	
	}
	
	
	@Test
	public static void Paytmmoviecode()
	{

	RestAssured.baseURI = "https://apiproxy.paytm.com/v2/movies/upcoming";
	RequestSpecification httpRequest = RestAssured.given();
	Response response = httpRequest.get();
	
	String json = response.asString();
	boolean flag = true;
	List<String> moviecode = JsonPath.read(json, "$.upcomingMovieData[*].paytmMovieCode");

	 HashSet<String> moviecodeunique = new HashSet(moviecode);
       
     if(moviecode.size() != moviecodeunique.size())
     {
			flag =false;
     }
     Assert.assertTrue(flag);

	}
	
	
	@Test
	public static void writemovies() throws Exception
	{

	RestAssured.baseURI = "https://apiproxy.paytm.com/v2/movies/upcoming";
	RequestSpecification httpRequest = RestAssured.given();
	Response response = httpRequest.get();
	
	String json = response.asString();
	
	List<String> moviecode = JsonPath.read(json, "$.upcomingMovieData[*].movie_name");

    XSSFWorkbook workbook = new XSSFWorkbook();
   
    XSSFSheet spreadsheet = workbook.createSheet( " Employee Info ");
    
    XSSFRow row;
    int rowid = 0;
    for (String key : moviecode) {
        row = spreadsheet.createRow(rowid++);
        int cellid = 0;
        Cell cell = row.createCell(cellid++);
        cell.setCellValue(key);
        
     }
	
    FileOutputStream out = new FileOutputStream(new File("./Movieslist.xlsx"));   
    workbook.write(out);
    out.close();
	
	}

}
