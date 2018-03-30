# Sensor profile template

This is a sensor profile template, it's meant to provide a framework for developing your own 4th Law profile interface with a physical sensor device.

Fleshing out this template should leave you with a node module that can be imported into the 4th Law framework. The scripts you need to do this have already been included in the package.json

The default export (and thus all functions that need to be accessible through the profile) should be included in the index.ts file. Any tests you want to run for validation should be inside of the profile.test.ts file, inside of the test folder.

Any non-javascript/typescript code that you need to run your profile should be included in the project folder, you will need to establish the correct linking between this code and the ts/js code that defines the module.

There are only two required functions, data() and info().

data() is a synchronous getter method, and is simply called as profile.data, returning the last data point (which is stored by the profile as the private variable \_lastDataPoint) recorded by the profile. You are responsible for making sure that the return of data() is correct, both in the sense that the format is correct, and in the sense that the value of \_lastDataPoint is up to date.

As defined in the 4th Law typings repo, sensor output data has the following format:
```
_lastDataPoint = {
    name: this.name,
    id: this._id,
    ts: moment().valueOf(),
    data: this._lastDataPoint,
  }
```
Each of these variables is described in the 4th-law typings repo, but they are redescribed here for convenience:
  - name is a string for holding the name of the sensor that is the source of the data.
  - id is a string for holding the id of the specific sensor, in the case that you have multiple of the same sensor with the same profile in your use-case.
  - ts is a number holding the timestamp of this particular data point.
  - data can be of any type, and is determined purely by your use-case.

info() is called as profile.info(), and returns information about this profile. Some information that is generic to all profiles will be returned, though you are responsible for setting the appropriate values of the variables returned (such as the name of your profile).

As defined in the 4th Law typings repo, sensor profile info has the following format:
```
info = {
    category: this._category,
    type: this._type,
    make: this._make,
    model: this._model,
    id: this._id,
    version: this._version,
    specs: this._specs,
  }
```
Each of these variables is described in a comment in index.ts, but they are redescribed here for convenience:
  - category is a string that describes whether this is a SENSOR or ACTUATOR. In this case, it's a SENSOR
  - type is a string that describes what type of SENSOR or ACTUATOR this is, which for sensors is currently drawn from the set ```["THERMAL", "ELECTRICAL", "ACOUSTIC", "CHEMICAL", "ENVIRONMENTAL", "PROXIMITY", "FORCE", "PRESSURE", "OPTICAL", "POSITIONAL", "NAVIGATIONAL", "IONIC", "FLUIDIC", "AUTOMOTIVE"]```
  - make is a string that identifies the manufacturer of your sensor
  - model is a string that identifies the model of your sensor
  - id is a string that describes the product id of your sensor, which should be provided by your vendor
  - version is a string formatted as "vX.X.X", and helps you keep track of the version of your profile. We also keep track of the version in the package.json, so you if you change the version variable in your profile, you should also change the version variable in package.json
  - specs is an optional variable you will need to instantiate yourself in the profile as a private variable, and should hold information that is specific to your parituclar sensor, such as the framerate of a camera or the operational range of a thermometer. It is not automatically returned by info, so you will have to overwrite the info function to return it in the info object.

Each of the above function calls can be overwritten to define functionality specific to your use-case.

Any other functions you need to make your profile work, such as helper functions, should also be included inside of index.ts as private functions.

If there are variables that you'd like to control externally to the actual javascript code, you can do so through the default.json file, provided in the config folder under the main module directory. Simply use the config node module to pull values encoded in the default.json file into your profile, as is shown for example by the setting of the \_sampleInterval variable in our constructor.
