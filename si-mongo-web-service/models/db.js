require('dotenv').config();
const mongoose = require('mongoose');
const dbURI = process.env.MONGO_URL;

mongoose.connect(dbURI, {
  useNewUrlParser: true,
  useUnifiedTopology: true
});

const gracefulShutdown = (msg, callback) => {
  mongoose.connection.close(() => {
    console.log("Mongoose disconnected through " + msg);
    callback();
  })
};

// LISTEN TO TERMINATIONS
process.once('SIGUSR2', () => {
  gracefulShutdown("nodemon restart", () => {
    process.kill(process.pid, "SIGUSR2");
  });
});

process.on('SIGINT', () => {
  gracefulShutdown("app termination", () => {
    process.exit(0);
  });
});

process.on('SIGTERM', () => {
  gracefulShutdown("Heroku termination", () => {
    process.exit(0);
  });
});

// LOG CHANGES TO MONGOOSE CONNECTION
mongoose.connection.on('connected', () => {
  console.log("Mongoose connected to " + dbURI);
});

mongoose.connection.on('error', err => {
  console.log("Mongoose connection error: " + err + ".");
});

mongoose.connection.on('disconnected', () => {
  console.log("Mongoose disconnected.");
});

require('./post');
