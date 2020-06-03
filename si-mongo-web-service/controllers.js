require('dotenv').config();
const MongoClient = require('mongodb').MongoClient;
let mongoDatabase;

MongoClient.connect(process.env.MONGO_URL, (err, client) => {
  if (err) {
    return console.error(err);
  }

  mongoDatabase = client.db(process.env.MONGO_DB_NAME);

  // TODO: Drop all collections
  // mongoDatabase.listCollections().toArray()
  //   .then(res => {
  //     const collections = res.map((collection) => collection.name);
  //
  //     collections.forEach(collection => {
  //       console.log(`Dropping collection ${collection}`);
  //       mongoDatabase.collection(collection).drop();
  //     });
  //   });
});

// GET: http://localhost:3001/api/v1/posts
module.exports.allPosts = async (req, res) => {
  try {
    console.log('[Query]: Get all collections');
    const response = await mongoDatabase.listCollections().toArray();
    const collections = response.map((collection) => collection.name);
    res.send(collections);
  } catch {
    res.status(404);
    res.send({ error: "Collection doesn't exist!" });
  }
};

// GET: http://localhost:3001/api/v1/posts/<collectionName>
module.exports.findPostByCollectionName = async (req, res) => {
  const { collectionName } = req.params;

  try {
    console.log(`[Query]: Find collection by name: ${collectionName}`);
    const post = await mongoDatabase.collection(collectionName).find({}).toArray();
    res.send(post);
  } catch {
    res.status(404);
    res.send({ error: `Collection with name ${collectionName} doesn't exist!` });
  }
};

// POST: http://localhost:3001/api/v1/posts/<collectionName>
module.exports.createCollection = async (req, res) => {
  try {
    const { collectionName } = req.params;

    console.log(`[Query]: Create collection ${collectionName}`);
    await mongoDatabase.createCollection(collectionName);
    res.status(201).send();
  } catch {
    res.status(404);
    res.send({ error: "Post doesn't exist!" });
  }
};

// POST: http://localhost:3001/api/v1/posts/insert/<collectionName>
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

    if (Array.isArray(document)) {
      console.log(`[Query]: Insert documents ${JSON.stringify(document)} in collection ${collectionName}`);
      await mongoDatabase.collection(collectionName).insertMany(document);
    } else {
      console.log(`[Query]: Insert document ${JSON.stringify(document)} in collection ${collectionName}`);
      await mongoDatabase.collection(collectionName).insertOne(document);
    }

    res.send(document);
  } catch {
    res.status(404);
    res.send({ error: "Post doesn't exist!" });
  }
};

// PATCH: http://localhost:3001/api/v1/posts/<collectionName>
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
  const update = { '$set': value };

  try {
    const post = await mongoDatabase.collection(collectionName).updateOne(condition, update);
    console.log(`[Query]: Update post ${post}`);
    res.send(post);
  } catch {
    res.status(404);
    res.send({ error: `Post with data ${req.body.data} doesn't exist!` });
  }
};

// DELETE: http://localhost:3001/api/v1/posts/<collectionName>
module.exports.deletePost = async (req, res) => {
  const { collectionName } = req.params;
  const value = req.body;

  try {
    console.log(`[Query]: Delete post by value: ${JSON.stringify(value)} from collection ${collectionName}`);
    await mongoDatabase.collection(collectionName).deleteOne(value);
    res.status(204).send();
  } catch {
    res.status(404);
    res.send({ error: `Document with value ${JSON.stringify(value)} doesn't exist!` });
  }
};

// GET: http://localhost:3001/api/v1/posts/query/<collectionName>
module.exports.complexQuery = async (req, res) => {
  const { collectionName } = req.params;
  const { values, query, sortCriteria, limitCriteria } = req.body.data;

  let finalQuery = {};

  if (query.hasOwnProperty('regexCriteria')) {
    switch (query['regexCriteria']) {
      case 'starts_with':
        // Start with string
        finalQuery = { zip: { '$regex': '^string', '$options': 'i' } };
        break;
      case 'ends_with':
        // Ends with string
        finalQuery = { zip: { '$regex': 'string$', '$options': 'i' } };
        break;
      case 'contains':
        // Contains string
        finalQuery = { zip: { '$regex': 'string', '$options': 'i' } };
        break;
      default:
        // Start with string
        finalQuery = { zip: { '$regex': '^string', '$options': 'i' } };
        break;
    }

    try {
      console.log(`[Query]: Complex query: ${JSON.stringify(finalQuery)} from collection ${collectionName}`);
      const result = await mongoDatabase
        .collection(collectionName)
        .find(finalQuery)
        .sort(sortCriteria)
        .limit(limitCriteria);
      res.send(result);
    } catch {
      res.status(404);
      res.send({ error: `Document with values ${JSON.stringify(finalQuery)} doesn't exist!` });
    }
  } else {
    try {
      console.log(`[Query]: Complex query: ${JSON.stringify(values)} from collection ${collectionName}`);
      const result = await mongoDatabase
        .collection(collectionName)
        .find(values)
        .sort(sortCriteria)
        .limit(limitCriteria);
      res.send(result);
    } catch {
      res.status(404);
      res.send({ error: `Document with values ${JSON.stringify(values)} doesn't exist!` });
    }
  }
};
