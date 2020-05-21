require('dotenv').config();

const arangojs = require("arangojs");
const arangoDatabase = new arangojs.Database({
  url: process.env.ARANGO_URL,
  databaseName: process.env.ARANGO_DB_NAME,
});

arangoDatabase.useDatabase(process.env.ARANGO_DB_NAME);

// GET: http://localhost:3002/api/v1/posts
module.exports.allPosts = async (req, res) => {
  try {
    console.log('[Query]: Get all collections');
    const response = await arangoDatabase.listCollections();
    const collections = response.map((collection) => collection.name);
    res.send(collections);
  } catch {
    res.status(404);
    res.send({ error: "Collection doesn't exist!" });
  }
};

// GET: http://localhost:3002/api/v1/posts/<collectionName>
module.exports.findPostByCollectionName = async (req, res) => {
  res.send({ text: 'findPostByCollectionName' });
};

// POST: http://localhost:3002/api/v1/posts/<collectionName>
module.exports.createCollection = async (req, res) => {
  const { collectionName } = req.params;

  try {
    const collection = arangoDatabase.collection(collectionName);
    await collection.create();
    res.status(201).send();
  } catch {
    res.status(404);
    res.send({ error: `${collectionName} already exists!` });
  }
};

// POST: http://localhost:3002/api/v1/posts/insert/<collectionName>
//
// Body:
// {
//    "TIP_UST": "MO",
//    "TIP_NAZIV": "Naziv"
// }
module.exports.insertPost = async (req, res) => {
  try {
    const { collectionName } = req.params;
    const document = req.body.data;
    const collection = arangoDatabase.collection(collectionName);

    if (Array.isArray(document)) {
      console.log(`[Query]: Insert documents ${JSON.stringify(document)} in collection ${collectionName}`);
      await collection.import(document);
    } else {
      console.log(`[Query]: Insert document ${JSON.stringify(document)} in collection ${collectionName}`);
      await collection.save(document);
    }

    res.send(document);
  } catch {
    res.status(404);
    res.send({ error: "Post doesn't exist!" });
  }
};

// PATCH: http://localhost:3002/api/v1/posts/<collectionName>
//
// Body:
// {
//     "data": {
//         "value": {
//             "TIP_UST": "XL",
//             "TIP_NAZIV": "Naziv"
//         },
//         "condition": {
//             "TIP_UST": "MO"
//         }
//     }
// }
module.exports.updatePost = async (req, res) => {
  const { collectionName } = req.params;
  const { value, condition } = req.body.data;

  try {
    const collection = arangoDatabase.collection(collectionName);
    const post = await collection.update(condition, value);
    console.log(`[Query]: Update post ${post}`);
    res.send(post);
  } catch {
    res.status(404);
    res.send({ error: `Post with data ${JSON.stringify(req.body.data)} doesn't exist!` });
  }
};

// DELETE: http://localhost:3002/api/v1/posts
module.exports.deletePost = async (req, res) => {
  res.send({ text: 'deletePost' });
};
