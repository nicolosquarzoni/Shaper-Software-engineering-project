
**Progetto di Fondamenti di Ingengeria del Software**






**Nicolò Squarzoni**








**a.a. 2021/2022**

**Shaper**

**Overall description:**

Shaper is a web based application to support dietologists, nutritionists and personal trainers to manage in an easy and efficient way all the activities with their patients. On the other hand it helps patients to correctly and effectively follow the professionals prescriptions, to track progresses and results.

**Functional requirements:**

1) All users can access the Shaper home page where they have to possibility to sign in with a pre-registered account or create a new account
1) Two types of accounts can be created: patient and professional
1) Top create a new account the new user has to fill a first form where he inserts his general information and the type of account he wants to create. If the form is not correctly filled an error message will pop up.
1) Then depending on the type of account a second form with specific information has to be fille in order to complete the registration. If the form is not correctly filled an error message will pop up.
1) Once completed the registration the new user can sign in from a dedicated page.  If the form is not correctly filled an error message will pop up.
1) If the user is a patient the home page has a link on the left to review and edit the personal information.

A section with the lists of all the diet plans and workout plans creted by his professionals that is empty if no plans are available. For each plan there is a link to access the details.

For diet plans they must display the list of all the options available. Each option has a link to display all the meals details of that specific option.

For workout plans they must display the list of all the workoutss available. Each workout has a link to display all the exercises details of that specific workout.

1) O the right there is a section to review the list of all the professional that are following the patient. Each name has a link to review all the details of that specific professional.
1) Below there is a link to search all the available professional with the possibility to filter by name, profession and service offered. A list appear below with a link for each name. The link shows the professional profile an below there is a section to send a request to the professional with a field requesting the services needed and a customized message for the professional. By sending the request the professional with appear in a dedicated section “sent requests” of the patient's home page.
1) If the user is a professional when logged in he is redirected to a dedicated home page.
1) The home page has a link on the left to review and edit the personal information.
1) Then there is a section with a list of all the followed patients which is empty if there are none and a section with a list of new patients requests.
1) In the first list each patient name is a link to the patient's  profile, which is similar to the page available for the patient but with the difference that the professional can create new plans and update the existing ones. Also in the page the professional can view the filled questionnaire he has sent while accepting the patient's request.
1) In the second section all the incoming requesting patients have a link that redirects to their personal information plus the details of the request the patient has sent.
1) The professional can accept the request and create a questionnaire with all the information he needs.
1) Once the request is accepted the professional's name will appear in the patient's home page in the section ''my professionals” .
1) Clicking the name the details of the professional can be viewed and also the patient can answer all the questions of the questionnaire sent by the professional. Once they are sent the patient in the same page can view the answers and as well the professional in the patient's page.
1) The professional can create and updated all the plans and the patient will see them in his home page as soon as they are saved.

**Non Functional requirements:**

1) Shaper should be available as a web application

**Story:**

Nick is a young professional who wants to improve his physical shape, building muscles and strength.

He got the contact of an experienced personal trainer, Steve, who lives far away. Nick contacts Steve via Shaper and he accepts to be Nick’s fitness consultant.

Steve creates and sends to Nick a customized form to fill in, to assess Nick’s lifestyle and his physical shape other than his physical goals. In the form three full body pictures from three sides are requested to track the physical improvements.

Based on his professional knowledge Steve creates a diet plan and a workout plan for the following month.

Both plans are loaded in the app and Nick can see his daily diet with details of each meal and his daily workout. From the app Nick is able to confirm for each meal and workout if he did it or not.

There is also an open field for both workouts and meals to send feedbacks  to Steve in case something has to be adjusted.

After two weeks Steve sets a check point and requests Nick to upload three new pictures, the weight and further remark if he has.

Based on the results Steve will decide if it is necessary to adjust the plans.

**Scenarios:**

**Scenario 1a:**

**Initial:** The patient can access the web app home page and he has the possibility to subscribe or sign in.

**Normal:** Nick clicks the subscribe button and he is redirected to a form where he can add his basic personal information and as optional some physical details like a picture, weight, height, sport routine, allergies ecc..

Nick clicks submit at the end of the form to complete the subscription.

**What can go wrong:** Nick is already registered and a warning message will pop up. The password double check does not match and a warning message will pop up. Nick did not fill all the required field of the form and a warning message will pop up. Picture size or format does not comply and a warning message will pop up.

**System state on completion:** clicking submit at the end of the form Nick will be redirected to the home page and he will be able to sign in.

**Scenario 1b:**

**Initial:** The professional can access the web app home page and he has the possibility to subscribe or sign in.

**Normal:** Steve clicks the subscribe button and he is redirected to a form where he can add his basic personal information, his professional information, a professional picture and as optional his professional certificates.

Steve clicks submit at the end of the form to complete the subscription.

**What can go wrong:** Steve is already registered and a warning message will pop up. The password double check does not match and a warning message will pop up. Steve did not fill all the required field of the form and a warning message will pop up. Picture size or format does not comply and a warning message will pop up.

**System state on completion:** clicking submit at the end of the form Steve will be redirected to the home page and he will be able to sign in.

**Scenario 2a:**

**Initial:** The patient can access the web app home page and he has the possibility to subscribe or sign in.

**Normal:** Nick clicks the sign in button, signs in, and he is redirected to his personal home page. There is a link to review and edit his personal information, a section to access the details of his training and diet plans which is going to be empty and a section to search professionals with different search filters such as name, profession, services offered. Nick clicks in the professionals search section, chooses the filter personal trainer, opens Steve's page and sends the consultation request to Steve adding the kind of service he needs and a message . Then he waits until Steve confirms the request.

**What can go wrong:** Nick is not registered and a warning message will pop up. The password inserted is wrong and a warning message will pop up.

**System state on completion:** clicking the request button the button will be disabled and beside it will be written ‘’request sent, waiting for confirmation’’.

**Scenario 2b:**

**Initial:** The professional can access the web app home page and he has the possibility to subscribe or sign in.

**Normal:** Steve clicks the sign in button, signs in, and he is redirected to his personal home page. There is a link to review and edit his personal information, and a section to access the details of his patients and the new patients requests.  On the right he finds the new patients requests and on left the list of current patients sorted by name. He opens Nick’s profile, he reviews his details, reads his requests and message, and after deciding to accept his request he clicks the ‘’Accept and create questionnaire’’ button, creates the questionnaire to send to Nick. By clicking the button ''Send questionnaire'' he sends to Nick the response.

**What can go wrong:**

**System state on completion:** clicking the confirmation button Steve is redirected to his home page and Nick's profile link is not anymore present in the patient requests section.

**Scenario 3:**

**Initial:** Nick can access the ''My professional” section in his account.

**Normal:** On top there is Steve’s section with his name as a link.

Nick clicks the link and he is redirected to a page where he can answer all Steve’s questions and review Steve's professional details.

Clicking the button "Send filled questionnaire" he submits the answers.

**What can go wrong:** Nick forgets to answer a question, and a warning will pop up.

**System state on completion:** clicking the "Send filled questionnaire" button Nick is redirected to his home page and clicking again on Steve's link he can see the saved filled questionnaire.

**Scenario 4:**

**Initial:** The professional accesses the page of the patient.

**Normal:** In Nick’s page Steve can find a link “View filled questionnaire” to the answered questionnaire page. By clicking it he can reed the answers and prepare the diet and the workout plans. If the questionnaire is not answered yet there will be only the questions. The page has a go back button to return to Nick’s page. Here there are two sections: the  diet plans section and the workout plans section. Entering them Steve can create new plans and update the existing ones. So he creates two new plans and clicking the ‘’create’’ button they are created.

**What can go wrong:** Some required fields are not filled in and a warning message will pop up.

**System state on completion:** clicking the create button the created plan will appear in the related plans section.

**Scenario 5:**

**Initial:** The patient accesses his profile page.

**Normal:** Nick accesses the plans section and he will find the plans that have been created by Steve divided in two categories: workout plans and diet plans. By clicking on each plan he can see the details and he can start getting fit. There is a go back button to return to the plans list.



**What can go wrong:**

**System state on completion:** clicking the plan button all plan details can be viewed.

**Technical observations:** the project is an MVP, the red parts in the scenarios have not been implemented for lack of time. The coding logic has evolved during the design due to the increase of my coding experience. There was no time to optimize all the code previously written.

**Unit Testing:** below a screenshot of the coverage factor of the unit tests. The methods coverage is lower due to a high number of methods in the app controller class that have been tested with Selenium.

**System Testing:** all the above scenarios have have a dedicated Selenium test that has passed. IMPORTANT: the system tests will work if launched all together. If launched singularly the code should be changed because the repository is created only during the first test.


