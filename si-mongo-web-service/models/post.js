const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Post = new Schema({
  title: String,
  content: String,
});

mongoose.model('Post', Post, 'posts');
