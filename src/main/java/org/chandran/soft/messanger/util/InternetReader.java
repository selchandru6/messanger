package org.chandran.soft.messanger.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;


public class InternetReader {
	public static double iNRValue = 0.0;
	public static double iScriptPrice = 0.0;
	public static boolean bookavailflag=false;
	private static String LIBRARY_URL="http://catalog.coppelltx.gov/polaris/search/searchresults.aspx?ctx=3.1033.0.0.6&type=Keyword&term=google&by=TI&sort=RELEVANCE&limit=TOM=bks&query=&page=0";
	
	private static Logger logger = Logger.getLogger(InternetReader.class);

	public static BufferedReader read(String url) throws Exception {
		return new BufferedReader(new InputStreamReader(new URL(url).openStream()));
	}

	public static BufferedReader bookRead(String urlStr) throws Exception {
		BufferedReader br=null;
		
		try{
			CookieManager cookieManager = new CookieManager();
			CookieHandler.setDefault(cookieManager);			
			
	//		cookieManager.setAcceptCookie(true);
     /*       URLConnection con = u.openConnection();

            con.setRequestProperty("Cookie", cookieManager.getInstance().getCookie(url););
            con.setDoOutput(true);
            con.connect();
            String addCookie = con.getHeaderField("Set-Cookie");
            System.out.println(con.getHeaderFields().toString());
            if (addCookie!=null) {
               // cookieManager.getInstance().setCookie(url, addCookie);
            }
*/
			URL url=new URL(urlStr); 	
			br= new BufferedReader(new InputStreamReader(new URL(urlStr).openStream()));
			HttpURLConnection conn = null;
			conn = (HttpURLConnection) url.openConnection();  
			URLConnection con = url.openConnection();
			//con.setRequestProperty("Cookie", cookieManager.getInstance().getCookie(url));
            con.setDoOutput(true);
            con.connect();
            String addCookie = con.getHeaderField("Set-Cookie");
            System.out.println(con.getHeaderFields().toString());
            /*if (addCookie!=null) {
                cookieManager.getInstance().setCookie(url, addCookie);
            }*/
			
		}catch(Exception ex){
			System.out.println("Error : "+ex);
			logger.error(ex);
		}
		
		return br;
	}

	
	public static void main(String[] args) throws Exception {
		// getINRRate();
	//	System.out.println(getScriptPrice("ctsh"));
		//getPERMProcessingTimes("test");
		// getPERMPDateAndTime();
		// getBookStatus();
		 
		 String weatherURL="https://weather.com/weather/today/l/75034:4:US";
		 System.out.println(readFromINet(weatherURL));

	}

	public static double getINRRate() throws Exception {
		boolean readFlag = false;
		BufferedReader reader = read("http://www.google.com/finance?q=USDINR");
		String line = reader.readLine();
		FileWriter fstream = new FileWriter("C:\\Users\\Sanjith\\IdeaProjects\\MyJavaBuddy\\out\\datafiles\\out.txt");
		BufferedWriter out = new BufferedWriter(fstream);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String iNRval = "";
		try {
			while (line != null) {
				if (line != null && line.trim().length() > 0)
					logger.debug(line);

				if (!readFlag && line != null && line.contains("1 USD = <span class=bld>")) {
					readFlag = true;
				}
				if (readFlag && line != null && line.trim().length() > 0) {
					out.write("\n" + line + "\n");
					iNRval = line.replaceAll("1 USD = <span class=bld>", "");
					iNRval = iNRval.replaceAll("INR</span>", "");
					logger.info(line.replaceAll("INR</span>", ""));
					logger.info(" The INR Value : " + iNRval);
					if (iNRval.length() > 1) {
						iNRValue = Double.parseDouble(iNRval);
					}
				}
				if (readFlag && line != null && line.contains("INR</span>")) {
					readFlag = false;
					break;
				}
				line = reader.readLine();
			}
		} catch (Exception e) {
			logger.debug("Exception in InternetReader.getINR " + e);
		} finally {
			out.write("Check the following Link too");
			out.write("\n https://www.google.com/search?q=1+USD+dollar+in+INR\n");
			out.write(dateFormat.format(date) + "\n");
			out.write("--------------------------------------------------------\n\n");
			out.close();
		}
		return iNRValue;
	}

	/*
	 * This Method used to pull the Script Price from Internet and store it in
	 * to the file Input Stipt Nmae Output : Current Market value
	 */

	public static double getScriptPrice(String script) throws Exception {
		boolean readFlag = false;
		BufferedReader reader = read("http://www.nasdaq.com/symbol/" + script.toUpperCase());
		String line = reader.readLine();
		FileWriter fstream = new FileWriter("C:\\Users\\Sanjith\\IdeaProjects\\MyJavaBuddy\\out\\datafiles\\out.txt");
		BufferedWriter out = new BufferedWriter(fstream);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String iScrp = "";
		try {
			while (line != null) {
				// System.out.println(line);
				if (line != null && line.trim().length() > 0)
					if (!readFlag && line != null && line.contains("qwidget-dollar")) {
						readFlag = true;
					}
				if (readFlag && line != null && line.trim().length() > 0) {
					iScrp = line.substring(59, 64);
					//iScrp.replace("<", "");
					String iScrprice = iScrp.replaceAll("[^a-zA-Z0-9.]+","");
					if (iScrprice.length() > 1 && !iScrprice.contains("<")) {
						iScriptPrice = Double.parseDouble(iScrprice);
					}else{
						iScriptPrice =0;
					}
					break;
				}
				line = reader.readLine();
			}
		} catch (Exception e) {
			System.out
					.println("Error in getScriptPrice method in the file InternetReader ***********"
							+ e);
			e.printStackTrace();
			e.initCause(e);
		} finally {
			// System.out.println(iScrp);
			out.write("Script Price for " + script.toUpperCase() + " is $" + iScriptPrice + " at ");
			// out.write("\n https://www.google.com/search?q=1+USD+dollar+in+INR\n");
			// out.write("**    $"+iScriptPrice+"   **");
			out.write(dateFormat.format(date) + "\n\n");
			out.write("\nhttp://www.nasdaq.com/symbol/" + script.toUpperCase());
			out.close();
		}
		return iScriptPrice;
	}

	public static void getPERMProcessingTimes(String filename) {

		String sCurrentLine;
		BufferedReader reader = null;
		BufferedWriter out = null;
		FileWriter fstream = null;
		boolean readStartFlag = false;
		boolean tbody = false;
		try {
			reader = read("https://icert.doleta.gov");

			fstream = new FileWriter("C:\\Users\\Sanjith\\IdeaProjects\\MyJavaBuddy\\out\\datafiles\\out.txt");
			out = new BufferedWriter(fstream);
			out.write("<html><head>"
					+ "<style>.al{font-family: Arial; color: #fff; text-align: center;font-size:100%; background-color:#009FEC;</style>"
					+ "</head><body>\n");
			sCurrentLine = reader.readLine();
			// System.out.println(sCurrentLine);
			while (sCurrentLine != null) {
				// System.out.println(sCurrentLine);
				if (sCurrentLine != null && sCurrentLine.contains("PERM Processing Times</strong>")) {
					readStartFlag = true;
				}
				if (readStartFlag && sCurrentLine != null) {
					if (sCurrentLine.contains("<tbody>")) {
						tbody = true;
					}
					// System.out.println(sCurrentLine);
					if (sCurrentLine.contains("table class=")) {
						StringBuffer sb = new StringBuffer(sCurrentLine);
						sb.replace(sCurrentLine.indexOf("=\"0\"") + 2,
								sCurrentLine.indexOf("=\"0\"") + 3, "1");
						out.write(sb.toString());
					} else if (tbody && sCurrentLine.contains("<tr>")) {
						out.write("\n			<tr class=\"al\">\n");
						tbody = false;
					} else {
						out.write(sCurrentLine + "\n");
					}
				}
				if (sCurrentLine != null && sCurrentLine.contains("</table>")) {
					readStartFlag = false;
				}
				sCurrentLine = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			System.out.println("Error " + ex.initCause(ex));
		} finally {
			try {
				out.write("</body></html>");
				if (out != null) {
					out.close();
				}
			} catch (Exception ex) {
				System.out.println(" Exception " + ex.initCause(ex));
			}
		}
	}

	public static String getPERMPDateAndTime() {

		BufferedReader br = null;
		boolean auditReview = true;
		int arLineCnt = 0;
		String auditDate = "";
		String auditYear = "";

		try {
			getPERMProcessingTimes("test");
			String sCurrentLine;
			br = new BufferedReader(new FileReader("C:\\Users\\Sanjith\\IdeaProjects\\MyJavaBuddy\\out\\datafiles\\out.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				if (auditReview && sCurrentLine.contains("Analyst Review")) {
					auditReview = false;
				} else if (!auditReview && arLineCnt < 2) {
					arLineCnt++;
					if (arLineCnt == 1) {
						logger.info("first line " + sCurrentLine);
						logger.info("first line Result "
								+ sCurrentLine.substring(84, (sCurrentLine.length() - 5)));
						auditDate = sCurrentLine.substring(84, (sCurrentLine.length() - 5));
						logger.info("auditDate:" + auditDate);
					}
					if (arLineCnt == 2) {
						// System.out.println("second line "+sCurrentLine);
						logger.info("second line "+sCurrentLine);
						logger.info("Second line Result "
								+ sCurrentLine.substring(97, (sCurrentLine.length() - 5)));
						auditYear = sCurrentLine.substring(97, (sCurrentLine.length() - 5));
						logger.info("auditYear:" + auditYear);
					}
				}
			}
		//	System.out.println("Analyst Reviews " + auditDate +" "+ auditYear);
			logger.info("Analyst Reviews " + auditDate +" "+ auditYear);
			/*
			 * if (!(auditDate + auditYear).equalsIgnoreCase("July 312012")) {
			 * System.out.println("The Data Changed from July 31 2012"); }
			 */
			return auditDate + " , " + auditYear;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} catch (Exception ex) {
			System.out.println("Error in PERM Processing Date Reading");
			ex.printStackTrace();
		}

		finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return auditDate + " , " + auditYear;
	}
	
	
/*
 * This Method used to read the value from Coppell Library Site and 
 * find out the status of the availability 
 */
	
	public static boolean getBookStatus() throws Exception {
		boolean readFlag = false;
		BufferedReader reader = bookRead(LIBRARY_URL);
		String line = reader.readLine();
		FileWriter fstream = new FileWriter("C:\\Users\\Sanjith\\IdeaProjects\\MyJavaBuddy\\out\\datafiles\\librarybookstxt.html");
		BufferedWriter out = new BufferedWriter(fstream);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String iNRval = "";
		try {
			while (line != null) {
				if (line != null && line.trim().length() > 0)
					logger.debug(line);
				    out.write("\n" + line + "\n");
/*
				if (!readFlag && line != null && line.contains("1 USD = <span class=bld>")) {
					readFlag = true;
				}
				if (readFlag && line != null && line.trim().length() > 0) {
					out.write("\n" + line + "\n");
					iNRval = line.replaceAll("1 USD = <span class=bld>", "");
					iNRval = iNRval.replaceAll("INR</span>", "");
					logger.info(line.replaceAll("INR</span>", ""));
					logger.info(" The INR Value : " + iNRval);
					if (iNRval.length() > 1) {
						iNRValue = Double.parseDouble(iNRval);
					}
				}
				if (readFlag && line != null && line.contains("INR</span>")) {
					readFlag = false;
					break;
				}
*/				line = reader.readLine();
			}
		} catch (Exception e) {
			logger.debug("Exception in InternetReader.getINR " + e);
		} finally {
			out.write("Check the following Link too");
			out.write("\n https://www.google.com/search?q=1+USD+dollar+in+INR\n");
			out.write(dateFormat.format(date) + "\n");
			out.write("--------------------------------------------------------\n\n");
			out.close();
		}
		return true;
	}

	public static String readFromINet(String webSiteURL) throws Exception {
		String htmlContent="";
		
		BufferedReader reader = read(webSiteURL);
		String line = reader.readLine();		
		try {
			while (line != null) {
				if(line.trim().length()>0)
				htmlContent+="\n"+line;
				line = reader.readLine();				
			}
		} catch (Exception e) {
			System.out.println("Error " + e);
			e.printStackTrace();
			e.initCause(e);
		}
		return htmlContent;
	}
	
	public static void prepareHTML(String sourceFile, String outputfile) {

	}

	public static double getiNRValue() {
		return iNRValue;
	}

	public static void setiNRValue(double iNRValue) {
		InternetReader.iNRValue = iNRValue;
	}

	protected static double getiScriptPrice() {
		return iScriptPrice;
	}

	protected static void setiScriptPrice(double iScriptPrice) {
		InternetReader.iScriptPrice = iScriptPrice;
	}
}
