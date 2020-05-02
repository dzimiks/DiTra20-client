module.exports.test = (req, res) => {
  res.json({
    test: 'dzimiks'
  });
};

module.exports.users = (req, res) => {
  res.json({
    users: [
      {
        name: 'dzimiks',
        username: 'dzimi'
      },
      {
        name: 'miki',
        username: 'milan'
      }
    ]
  });
};
