# My Shopping List v. 1.0.0
My Shopping List client-server application allows users to manage their shopping lists content from shops registered in the system as a customer or manage their shops assortment as a shop manager. 
User needs to create account first by specifying their username, password and account type.
If customer account is chosen, user will be able to choose shop registered in the database and create shopping list in it. He can add products available in the shop to their list, share list with other users, clone, and delete them.
You can run multiple client applications at the same time and edit shared lists in real time.
If shop manager account is chosen, user will be able to create shops, add products in different categories and change their quantity.

# Server application commands
- help - prints available server commands
- time - shows server launch and working time
- clients - prints active clients connections
- users - prints logged in users data
- shops - prints shops loaded to server memory
- lists - prints customer lists loaded to server memory
- info - prints software info
- shutdown - shuts down the server

# Client application features
Both customer and shop manager can:
-	Create an account (customer or shop manager);
-	Change username;
-	Change password;
-	Delete account;
# Customer account
Customer can:
-	Create multiple shopping lists in each shop;
-	Add products from different categories in the shop to their shopping list;
-	Remove products from shopping list;
-	Remove whole categories from shopping list;
-	Change products quantity;
-	Monitor shopping list total value;
-	Change displayed shopping list currency (PLN, EUR, GBP, USD);
-	Save shopping list state;
-	Reload shopping list state (e. g. when mistakes are made);
-	Share shopping list with other users in read-only or full-access mode;
-	Clone shopping list;
-	Delete shopping list;
-	View other users shopping lists shared with him in read-only mode (he can still clone it);
-	Fully edit other users shopping lists shared with him in full-access mode (except deleting and sharing list further).

# Shop manager account
Shop manager can:
-	Create multiple shops;
-	Add products to different categories;
-	Edit products (change their name, price, quantity, unit size, unit type and currency);
-	Remove products from the shop;
-	Remove whole categories from the shop;
-	Monitor shop total value;
-	Change displayed shop currency (PLN, EUR, GBP, USD);
-	Save shop state to local shop database;
-	Reload shop state from local shop database;
-	Push shop state to public shop database;
-	Reload shop state from public shop database.

# Run instruction
1. Clone repository.
2. Compile and run server application first.
3. Compile and run client applocations.
