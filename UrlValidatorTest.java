/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import org.junit.Assert;

import junit.framework.TestCase;





/**
 * Performs Validation Test for url validations.
 *
 * @version $Revision: 1128446 $ $Date: 2011-05-27 13:29:27 -0700 (Fri, 27 May 2011) $
 */
public class UrlValidatorTest extends TestCase {

   private boolean printStatus = false;
   private boolean printIndex = false;//print index that indicates current scheme,host,port,path, query test were using.
   
   private ResultPair[] testUrlSchemes = {
         new ResultPair("", true),
		 new ResultPair("http://", true),
		 new ResultPair("https://", true),
         new ResultPair("ftp://", true),
         new ResultPair("htt://", false),
         new ResultPair("http:", false),
         new ResultPair("http:/", false),
         new ResultPair("https/", false),
         new ResultPair("///", false)			
         };
   private ResultPair[] testAuthorities = {
		   new ResultPair("www.google.com", true),
           new ResultPair("amazon.com", true),
           new ResultPair("uk.yahoo.com", true),
           new ResultPair("www.yahoo.co.uk", true),
           new ResultPair("yahoo.co.uk", true),
           new ResultPair("0.0.0.0", true),
           new ResultPair("192.168.0.1", true),
           new ResultPair("192.174.456.1", false),
           new ResultPair("223.com", true),
		   new ResultPair("www.google.ninja", true),
		   new ResultPair("www.google.net", true),
		   new ResultPair("www.google.gov", true),
		   new ResultPair("www.google.edu", true),
		   new ResultPair("www.google.io", true),
		   new ResultPair("docs.google.com", true),
		   new ResultPair("www.google.444", false),
           new ResultPair("192.145.3.2.", false),
           new ResultPair("192.168.1", false),
           new ResultPair(".4.3.4.5", false),
           new ResultPair("", false)};
   
   private ResultPair[] testPorts = {
		   new ResultPair(":80", true),
           new ResultPair(":65535", true),
           new ResultPair(":0", true),
           new ResultPair("", true),
           new ResultPair(":-1", false),
           new ResultPair(":65636", true),
           new ResultPair(":65a", false)
           };
   
   private ResultPair[] testPaths = {
		   new ResultPair("/mail/u/2", true),
           new ResultPair("/mail", true),
           new ResultPair("/$37", true),
           new ResultPair("/mail/", true),
           new ResultPair("/..", false),
           new ResultPair("/../", false),
           new ResultPair("", true),
           new ResultPair("/", true),
           new ResultPair("/..//mail", false),
           new ResultPair("/inbox//mail", false)
           };
   
   private ResultPair[] testQueries = {
		   new ResultPair("", true),
           new ResultPair("?twenty=20&UnitID=439", true),
           new ResultPair("?stuff=true", true)
           };

   public UrlValidatorTest(String testName) {
      super(testName);
   }
   
   public void testManualTest()
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
//	   System.out.println(urlVal.isValid("http://www.amazon.com"));
	   
   }
   
   //Test schemes
   public void testYourFirstPartition()
   {
	   for(int i = 0; i < this.testUrlSchemes.length; i++) {
		   String testURL = "";
		   testURL = testURL + this.testUrlSchemes[i].item;
		   testURL = testURL + "www.google.com";
		   validate(testURL, this.testUrlSchemes[i].valid);
	   }
   }
   
   //Test Authorities
   public void testYourSecondPartition(){
	   for(int i = 0; i < this.testAuthorities.length; i++) {
		   String testURL = "http://";
		   testURL = testURL + this.testAuthorities[i].item;
		   validate(testURL, this.testAuthorities[i].valid);
	   }
   }
   
   //Test Paths
   public void testYourThirdPartition(){
	   for(int i = 0; i < this.testPaths.length; i++) {
		   String testURL = "http://www.google.com";
		   testURL = testURL + this.testPaths[i].item;
		   validate(testURL, this.testPaths[i].valid);
	   }
   }
   
   //Test Queries
   public void testYourFourthPartition(){
	   for(int i = 0; i < this.testQueries.length; i++) {
		   String testURL = "http://www.google.com";
		   testURL = testURL + this.testQueries[i].item;
		   validate(testURL, this.testQueries[i].valid);
	   }
   }
   
   //Test Ports
   public void testYourFifthPartition(){
	   for(int i = 0; i < this.testPorts.length; i++) {
		   String testURL = "http://www.google.com";
		   testURL = testURL + this.testPorts[i].item;
		   validate(testURL, this.testPorts[i].valid);
	   }
   }

   /**
    * The data given below approximates the 4 parts of a URL
    * <scheme>://<authority><path>?<query> except that the port number
    * is broken out of authority to increase the number of permutations.
    * A complete URL is composed of a scheme+authority+port+path+query,
    * all of which must be individually valid for the entire URL to be considered
    * valid.
    */
   
   public void testIsValid()
   {
//	   loopOverSchemes();
   }
   
   //
   public void loopOverSchemes() {
	   String testURL = "";
	   
	   for(int i = 0; i < this.testUrlSchemes.length; i++) {
		   testURL = "";
		   testURL = testURL + this.testUrlSchemes[i].item; 
		   loopOverAuthorities(testURL, this.testUrlSchemes[i].valid);
	   }
   }
   
   public void loopOverAuthorities(String testURL, boolean expectedResult) {
	   
	   for(int i = 0; i < this.testAuthorities.length; i++) {
		   String testURLTemp = testURL + this.testAuthorities[i].item; 
		   if(expectedResult && this.testAuthorities[i].valid) {
			   loopOverPorts(testURLTemp, true);
		   } else {
			   loopOverPorts(testURLTemp, false);
		   }
	   }
   }
   
   public void loopOverPorts(String testURL, boolean expectedResult) {
	   
	   for(int i = 0; i < this.testPorts.length; i++) {
		   String testURLTemp = testURL + this.testPorts[i].item; 
		   if(expectedResult && this.testPorts[i].valid) {
			   loopOverPaths(testURLTemp, true);
		   } else {
			   loopOverPaths(testURLTemp, false);
		   }
	   }
   }
   
   public void loopOverPaths(String testURL, boolean expectedResult) {
	   
	   for(int i = 0; i < this.testPaths.length; i++) {
		   String testURLTemp = testURL + this.testPaths[i].item; 
		   if(expectedResult && this.testPaths[i].valid) {
			   loopOverQueries(testURLTemp, true);
		   } else {
			   loopOverQueries(testURLTemp, false);
		   }
	   }
   }
   
   public void loopOverQueries(String testURL, boolean expectedResult) {
	   
	   for(int i = 0; i < this.testQueries.length; i++) {
		   String testURLTemp = testURL + this.testQueries[i].item; 
		   if(expectedResult && this.testQueries[i].valid) {
			   validate(testURLTemp, true);
		   } else {
			   validate(testURLTemp, false);
		   }
	   }
   }
   
   public void validate(String testURL, boolean expectedResult) {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
	   if(urlVal.isValid(testURL) != expectedResult) {
		   printResults(testURL, expectedResult, urlVal.isValid(testURL));
	   }
	   assertEquals(urlVal.isValid(testURL), expectedResult);
   }
   
   public void printResults(String testURL, boolean expectedResult, boolean result) {
	   String output = testURL + "  isValid(): " + result + "  expected: " + expectedResult;
	   System.out.println(output);
	   System.out.println("");
   }
   
   
   /**
    * Create set of tests by taking the testUrlXXX arrays and
    * running through all possible permutations of their combinations.
    *
    * @param testObjects Used to create a url.
    */
   

}
