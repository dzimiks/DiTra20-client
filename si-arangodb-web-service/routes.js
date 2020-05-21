const express = require('express');
const router = express.Router();

const mainController = require('./controllers');

// GET
router.get('/api/v1/posts', mainController.allPosts);
router.get('/api/v1/posts/:collectionName', mainController.findPostByCollectionName);

// POST
router.post("/api/v1/posts/:collectionName", mainController.createCollection);
router.post("/api/v1/posts/insert/:collectionName", mainController.insertPost);

// PATCH
router.patch("/api/v1/posts/:collectionName", mainController.updatePost);

// DELETE
router.delete("/api/v1/posts/:collectionName", mainController.deletePost);

module.exports = router;
