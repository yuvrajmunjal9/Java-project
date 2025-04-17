💬 Java Real-time Chat App (Console-based)
A simple yet powerful multi-client real-time chat application built with Java using sockets, supporting:

Public chat 🗣️

Private messaging 🔒

Real-time online users list 📋

Multiple clients at once 🌐

Built for fun, learning, and extending into future GUI or web-based versions.

📌 Features
✅ Real-time messaging between users
✅ Private chat with /pm <username> <message>
✅ List online users with /users
✅ Handles multiple clients concurrently
✅ Prevents duplicate usernames
✅ Clean server logging and graceful disconnection
✅ Fully console-based, perfect for CLI lovers and learners

🧠 How It Works
The ChatServer listens on port 12345 and spawns a new thread for each connected client.

Each ChatClient connects via socket, sends messages, and listens to responses in a separate thread.

Users can:

Chat publicly

Send private messages using /pm

View who's online using /users

