TAM - Time Aware Machine [![Build Status](https://travis-ci.org/ABeltramo/TAMDroid.svg?branch=master)](https://travis-ci.org/ABeltramo/TAMDroid) [![License: MIT](https://img.shields.io/badge/License-MIT-lightgrey.svg)](https://opensource.org/licenses/MIT)
==========

Time Aware Machine (TAM) is an Android object-oriented framework which simplifies the development of time aware applications (you can learn more [here](http://www.sal.disco.unimib.it/technologies/tam/).  

How to use
-------

The project contains already two test suite and one test application that you should see before starting.  

To define the behaviour of the framework it is necessary to write a valid JSON document, like the following one:

```json
{
  "GroundTimer": {
    "duration": 1,
    "child": [
      {
        "Timer": {
          "ID": "T1",
          "duration": 1,
          "child": [
            {
              "Performer": {
                "ID": "P1",
                "taskClass": "com.app.updateVisualTask",
                "taskConstructorKey": "p1"
              }
            }
          ]
        }
      },
      {
        "Timer": {
          "ID": "T2",
          "duration": 2,
          "child": [
            {
              "Performer": {
                "ID": "P2",
                "taskClass": "com.app.updateVisualTask",
                "taskConstructorKey": "p2"
              }
            },
            {
              "Timer": {
                "ID": "T3",
                "duration": 10,
                "child": [
                  {
                    "Performer": {
                      "ID": "P3",
                      "taskClass": "com.app.toastTask",
                      "taskConstructorKey": "p3"
                    }
                  }
                ]
              }
            }
          ]
        }
      }
    ]
  }
}
```

You can easily visualize the structure as a Tree where the leafs are **Performers** and the nodes ar **Timers** or **Clocks**.  

The root of the Tree is always a **GroundTimer** which can have a variable duration as any other Timer. For the **Performers** it is necessary to define which class is redefining **PerformerTask** and, if necessary, a key that represent which constructor needs to be passed at the creation of this object.

Once definited the JSON file it's necessary to instantiate the **Engine** class which will be in charge of the definition of the environment and the managment of Timers, Clocks and Performers.
```java
settings = new JSONObject(loadJSONFromAsset("sampleConfig.json"));
tamEngine = new Engine(null,settings,null);
```
Engine gets three parameters:
* **Timer realTimer**: An external timer which executes *tick()* with some interval.
* **JSONObject startOptions**: A JSON representation of the environment (just like the example before).
* **HashMap<String,Object> constructors**: [optional] A map that defines which objects must be passed as constructor of the different PerformerTasks.
The following is a more complete example of the creation of the Engine.

```java
settings = new JSONObject(loadJSONFromAsset("sampleConfig.json"));
HashMap constructors = new HashMap<String,Object>();
constructors.put("p1",new Object[]{this,R.id.contatore1});
constructors.put("p2",new Object[]{this,R.id.contatore2});
constructors.put("p3",new Object[]{getApplicationContext(),"P3: 20 tick"});
GroundTimerEx ground = new GroundTimerEx(new Handler());
tamEngine = new Engine(ground,settings,constructors);
```
Credits
-------
*Developed by: ABeltramo <[beltramo.ale@gmail.com](beltramo.ale@gmail.com)>*
	
License
-------
	The MIT License (MIT)

	Copyright (c) 2016 Alessandro Beltramo

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
