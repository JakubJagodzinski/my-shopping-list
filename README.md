# My Shopping List
My Shopping List client-server application allows users to manage their shopping lists content from shops registered in the system as a customer or manage their shops assortment as a shop manager. 

## Contents
- [General description](#general-description)
- [Server application screenshots](#server-application-screenshots)
- [Server application commands](#server-application-commands)
- [Client application features](#client-application-features)
- [Customer account screenshots](#customer-account-screenshots)
  - [Creating an account](#creating-an-account)
  - [Signing in as customer](#signing-in-as-customer)
  - [Creating new shopping list](#creating-new-shopping-list)
  - [Editing shopping list](#editing-shopping-list)
  - [Cloning shopping list](#cloning-shopping-list)
  - [Sharing shopping list with other customers](#sharing-shopping-list-with-other-customers)
  - [Account settings](#account-settings)
- [Customer account features](#customer-account-features)
- [Shop manager account screenshots](#shop-manager-account-screenshots)
  - [Signing in as shop owner](#signing-in-as-shop-owner)
  - [Choosing shop to manage](#choosing-shop-to-manage)
  - [Editing product](#editing-product)
  - [Adding new product](#adding-new-product)
  - [Creating new shop](#creating-new-shop)
- [Shop manager account features](#shop-manager-account-features)
- [Run instruction](#run-instruction)
- [Version](#version)
- [Author](#author)

## General description
User needs to create account first by specifying their username, password and account type.
If customer account is chosen, user will be able to choose shop registered in the database and create shopping list in it.
He can add products available in the shop to their list, share list with other users, clone, and delete them.
You can run multiple client applications at the same time and edit shared lists in real time.
If shop manager account is chosen, user will be able to create shops, add products in different categories and change their quantity.

## Server application screenshots

![Server terminal view](screenshots/server/server_terminal.png)
![Server commands used](screenshots/server/server_terminal_commands.png)

## Server application commands
- help - prints available server commands
- time - shows server launch and working time
- clients - prints active clients connections
- users - prints logged in users data
- shops - prints shops loaded to server memory
- lists - prints customer lists loaded to server memory
- info - prints software info
- shutdown - shuts down the server

## Client application features
Both customer and shop manager can:
-	Create an account (customer or shop manager);
-	Change username;
-	Change password;
-	Delete account;
  
## Customer account screenshots
### Creating an account
![Welcome screen](screenshots/client/welcome_screen.png)

### Signing in as customer
![Customer sign in](screenshots/client/client_sign_in.png)
![Customer sign up](screenshots/client/client_sign_up.png)

### Creating new shopping list
![Customer create list](screenshots/client/create_list.png)
![Customer choose shop](screenshots/client/choose_shop.png)
![Customer choose list](screenshots/client/choose_list.png)

### Editing shopping list
![Customer edit list](screenshots/client/edit_list.png)
![Customer add product](screenshots/client/add_product.png)
![Customer list with products owner view](screenshots/client/list_with_products_owner_view.png)

### Cloning shopping list
![Customer list cloning](screenshots/client/list_cloning.png)

### Sharing shopping list with other customers
#### Noone shared any list with us
![Customer shared lists empty](screenshots/client/shared_lists_empty.png)
#### Sharing list with other customer
![Customer share list](screenshots/client/share_list.png)
#### Now we can view (read-only) or edit (full-access) other customer's shopping list
![Customer shared lists](screenshots/client/shared_lists.png)
![Customer shared list full access view](screenshots/client/shared_list_full_acces_view.png)
![Customer shared list read only view](screenshots/client/shared_list_read_only_view.png)
#### Account settings
![Customer account settings](screenshots/client/account_settings.png)

## Customer account features
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

## Shop manager account screenshots

### Signing in as shop owner
![Shop manager sign in](screenshots/shop_manager/sign_in_manager.png)
![Shop manager menu](screenshots/shop_manager/manager_menu.png)

### Choosing shop to manage
![Shop manager choose shop](screenshots/shop_manager/choose_shop.png)
![Shop manager shop view](screenshots/shop_manager/shop_view.png)

### Editing product
![Shop manager shop edit](screenshots/shop_manager/shop_edit.png)
![Shop manager shop with edited](screenshots/shop_manager/shop_with_edited.png)

### Adding new product
![Shop manager shop add product](screenshots/shop_manager/shop_add_product.png)
![Shop manager shop with new product](screenshots/shop_manager/shop_with_new_product.png)

### Creating new shop
![Shop manager create new shop](screenshots/shop_manager/create_new_shop.png)
![Shop manager new empty shop](screenshots/shop_manager/empty_shop.png)

## Shop manager account features
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

## Run instruction
1. Clone repository.
2. Compile and run server application first.
3. Compile and run client applocations.

There is an example shop manager account with few created shops in the database for the showcase.

## Version
1.0.0

## Author
Jakub Jagodziński
