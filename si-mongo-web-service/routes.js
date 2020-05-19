const express = require('express');
const router = express.Router();

const mainController = require('./controllers');

// GET
router.get('/api/v1/posts', mainController.allPosts);
router.get('/api/v1/posts/:id', mainController.findPostById);

// POST
router.post("/api/v1/posts", mainController.insertPost);

// PATCH
router.patch("/api/v1/posts/:id", mainController.updatePost);

// DELETE
router.delete("/api/v1/posts/:id", mainController.deletePost);

module.exports = router;
