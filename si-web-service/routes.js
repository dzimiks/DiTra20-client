const express = require('express');
const router = express.Router();

const mainController = require('./controllers');

router.get('/', mainController.test);
router.get('/api/v1/users', mainController.users);

module.exports = router;
