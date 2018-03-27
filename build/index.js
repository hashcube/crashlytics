var Promise = require('bluebird');
var fs = Promise.promisifyAll(require('fs-extra'));
var path = require('path');
var xmldoc = require('xmldoc');

exports.onCreateProject = function (api, app, config, cb) {

  var outputPath = config.outputPath,
  copyCustomRules = function (outpath) {
    var file = 'custom_rules.xml',
      srcPath = path.join(__dirname, '../android', file),
      buildXml = fs.readFileSync(srcPath, 'utf-8'),
      xmlDoc = new xmldoc.XmlDocument(buildXml);

    xmlDoc.attr.name = app.manifest.shortName;
    xmlDoc.firstChild.attr.file = path.join(__dirname, '../android',
      'devtool/crashlytics_build.xml');

    return fs.outputFileAsync(path.join(outpath, file), xmlDoc.toString(), 'utf-8');
  };

  if (config.target === 'native-android') {
    return copyCustomRules(outputPath)
      .then(cb);
  } else if (config.target === 'native-ios') {
    var buildScipt = path.join(__dirname, '../ios', 'buildScript'),
      manifest = app.manifest.ios,
      scriptContent = fs.readFileSync(buildScipt, 'utf-8');

    outputPath = path.join(outputPath, 'xcodeproject');
    return fs.outputFileAsync(path.join(outputPath, 'resources', 'extra'),
      scriptContent.toString() + ' ' + manifest.crashlyticsKey +
        ' ' + manifest.crashlyticsBuildSecret, 'utf-8')
        .then(cb);
  }
};
