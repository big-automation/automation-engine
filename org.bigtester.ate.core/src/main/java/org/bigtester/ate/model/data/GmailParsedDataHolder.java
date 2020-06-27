/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2015, Montreal PROT, or individual contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Montreal PROT.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.bigtester.ate.model.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.bigtester.ate.constant.EnumRunTimeDataType;
import org.bigtester.ate.constant.ExceptionErrorCode;
import org.bigtester.ate.constant.ExceptionMessage;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.page.IPageObject;

import com.bigtester.casestep.GetIndeedVerificationCodeFromGmail;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePartHeader;

// TODO: Auto-generated Javadoc
/**
 * This class PageParsedDataHolder defines ....
 * @author Peidong Hu
 *
 */
public class GmailParsedDataHolder extends AbstractRunTimeInputDataHolder implements IStepInputData, IDataParser{
    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens3";
    private static final String indeedCodeSubjectKeyworkds = " is the verification code to apply for: ";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    
    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GetIndeedVerificationCodeFromGmail.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private String parseIndeedCode(String subject) {
    	if (subject.contains(indeedCodeSubjectKeyworkds))
    		return subject.substring(0, subject.indexOf(indeedCodeSubjectKeyworkds)).trim();
    	else 
    		return "";
    	//- 681010 is the verification code to apply for: Lead Digital DevOps Engineer
    	
    }
	
	/**
	 * @param dataType
	 * @param pageHtmlLeftBoundry
	 * @param pageHtmlRightBoundry
	 * @param page
	 * @param springBeanID
	 */
	public GmailParsedDataHolder(EnumRunTimeDataType dataType,
			String springBeanID) {
		super(dataType, null, null, null,
				springBeanID);
		super.setParseDataBeforeAction(true);
		
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void parseData() throws RuntimeDataException {
		
		try {
			// Build a new authorized API client service.
			//BatchGoogleMailClientFactory mailFactory = new BatchGoogleMailClientFactory();
			//Gmail service = mailFactory.makeClient(GAPICLINETID, GAPISECRET, GAPIAPPNAME, "", "");
			
	        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
	                .setApplicationName(APPLICATION_NAME)
	                .build();

	        // Print the labels in the user's account.
	        String user = "me";
	        ListMessagesResponse listResponse = service.users().messages().list(user).execute();
	        List<Message> labels = listResponse.getMessages();
	        List<Message> messages = new ArrayList<Message>();
	        for (Message label : labels) {
	        	messages.add(service.users().messages().get(user, label.getId()).execute());
	        }
	        labels = messages;
	        labels = labels.stream().sorted(Comparator.comparingLong(Message::getInternalDate).reversed()).collect(Collectors.toList());
	        if (labels.isEmpty()) {
	            System.out.println("No labels found.");
	        } else {
	            System.out.println("Subjects:");
	            for (Message label : labels) {
	            	//Message msg = service.users().messages().get(user, label.getId()).execute();
	            	List<MessagePartHeader> mheader = label.getPayload().getHeaders().stream().filter(header -> {
	                	//System.out.println("header name is:" + header.getName());
	                	return header.getName().equalsIgnoreCase("Subject") && header.getValue().contains(indeedCodeSubjectKeyworkds);
	                }).collect(Collectors.toList());
	            	if (mheader.size()==1) {
	            		String code = parseIndeedCode(mheader.get(0).getValue());
	            		System.out.printf("- %s\n", code);
	            		setStrDataValue(code);
	            		break;
	            	}
	            }
	        }
	        System.out.println("testing");
		} catch (IOException | GeneralSecurityException e) {
			RuntimeDataException rde = new RuntimeDataException(ExceptionMessage.MSG_RUNTIMEDATA_NOTFOUND, ExceptionErrorCode.RUNTIMEDATA_NOTFOUND, e);
			rde.setTestCaseName("java");
			rde.setTestStepName("javastep");
			rde.initAteProblemInstance(this).setFatalProblem(true);
			throw rde;
		}
		
	}

	
	

}
