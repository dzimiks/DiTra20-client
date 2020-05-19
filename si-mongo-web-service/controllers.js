const mongoose = require('mongoose');
const Post = mongoose.model('Post');

// GET: http://localhost:3001/api/v1/posts
module.exports.allPosts = async (req, res) => {
  try {
    console.log('[Query]: Get all posts');
    const posts = await Post.find();
    res.send(posts);
  } catch {
    res.status(404);
    res.send({ error: "Posts don't exist!" });
  }
};

// GET: http://localhost:3001/api/v1/posts/5ec3da3b81c2fb94b155cea8
module.exports.findPostById = async (req, res) => {
  try {
    console.log(`[Query]: Find post by id: ${req.params.id}`);
    const post = await Post.findOne({ _id: req.params.id });
    res.send(post);
  } catch {
    res.status(404);
    res.send({ error: `Post with ID ${req.params.id} doesn't exist!` });
  }
};

// POST: http://localhost:3001/api/v1/posts
//
// Body:
// {
// 	"title": "New post",
// 	"content": "Post content."
// }
module.exports.insertPost = async (req, res) => {
  try {
    const post = new Post({
      title: req.body.title,
      content: req.body.content,
    });

    console.log(`[Query]: Insert post ${post}`);
    await post.save();
    res.send(post);
  } catch {
    res.status(404);
    res.send({ error: "Post doesn't exist!" });
  }
};

// PATCH: http://localhost:3001/api/v1/posts/5ec3db943f3e0d958b17337a
//
// Body:
// {
// 	"title": "Updated post",
// 	"content": "Updated post content."
// }
module.exports.updatePost = async (req, res) => {
  try {
    const post = await Post.findOne({ _id: req.params.id });

    if (req.body.title) {
      post.title = req.body.title;
    }

    if (req.body.content) {
      post.content = req.body.content;
    }

    console.log(`[Query]: Update post ${post}`);
    await post.save();
    res.send(post);
  } catch {
    res.status(404);
    res.send({ error: `Post with ID ${req.params.id} doesn't exist!` });
  }
};

// DELETE: http://localhost:3001/api/v1/posts/5ec3db943f3e0d958b17337a
module.exports.deletePost = async (req, res) => {
  try {
    console.log(`[Query]: Delete post by id: ${req.params.id}`);
    await Post.deleteOne({ _id: req.params.id });
    res.status(204).send();
  } catch {
    res.status(404);
    res.send({ error: `Post with ID ${req.params.id} doesn't exist!` });
  }
};
