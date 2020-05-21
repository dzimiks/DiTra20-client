require('dotenv').config();

const arangojs = require("arangojs");
const arangoDatabase = new arangojs.Database({
  url: process.env.ARANGO_URL,
  databaseName: process.env.ARANGO_DB_NAME,
});

const createCollection = async () => {
  try {
    await collection.create();
    console.log(`Collection ${COLLECTION_NAME} is created`);
  } catch (err) {
    console.error('Failed to create collection:', err);
  }
};

const COLLECTION_NAME = 'dzimiks';
arangoDatabase.useDatabase(process.env.ARANGO_DB_NAME);
const collection = arangoDatabase.collection(COLLECTION_NAME);
createCollection().then(result => console.log(`${COLLECTION_NAME} is working!`));

// GET: http://localhost:3002/api/v1/posts
module.exports.allPosts = async (req, res) => {
  res.send({ text: 'allPosts:' });
};

// GET: http://localhost:3002/api/v1/posts/<collectionName>
module.exports.findPostByCollectionName = async (req, res) => {
  res.send({ text: 'findPostByCollectionName' });
};

// POST: http://localhost:3002/api/v1/posts/<collectionName>
module.exports.createCollection = async (req, res) => {
  res.send({ text: 'createCollection' });
};

// POST: http://localhost:3002/api/v1/posts/insert/<collectionName>
module.exports.insertPost = async (req, res) => {
  res.send({ text: 'insertPost' });
};

// PATCH: http://localhost:3002/api/v1/posts/<collectionName>
module.exports.updatePost = async (req, res) => {
  res.send({ text: 'updatePost' });
};

// DELETE: http://localhost:3002/api/v1/posts
module.exports.deletePost = async (req, res) => {
  res.send({ text: 'deletePost' });
};
