TAM - Time Aware Machine [![Build Status](https://travis-ci.org/ABeltramo/TAMDroid.svg?branch=master)](https://travis-ci.org/ABeltramo/TAMDroid)
==========

Il progetto consiste nella creazione di un Framework per agevolare lo sviluppo di applicazioni che necessitano di una cognizione di tempo.  
TAM espone un’API per la definizione di “azioni” (Performer) e di “cronometri” (Timer, Clock). Allo scadere di un cronometro questo attiverà altri cronometri collegati ad esso e/o delle azioni da eseguire.

Utilizzo
-------

Il progetto contiene già due test suite e un applicazione di esempio che possono essere consultate per avere un'idea di come utilizzare il framework.  

Per definire il comportamento del framework è necessario innanzitutto scrivere un documento JSON valido. Un esempio è il seguente:
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
La struttura è facilmente visualizzabile come un albero dove le foglie sono **Performer** ed i nodi sono **Timer** o **Clock**.  
La radice dell'albero è sempre un **GroundTimer** il quale può avere una durata variabile come qualsiasi Timer.  
Per i **Performer** è necessario definire quale classe ridefinisce PerformerTask e se necessario una chiave che definisce quale costruttore deve essere passato alla creazione di questo oggetto.

Una volta definito il file JSON è necessario istanziare l'Engine il quale si occuperà della definizione dell'ambiente e della gestione dei Timer,Clock e Performer.
```java
settings = new JSONObject(loadJSONFromAsset("sampleConfig.json"));
tamEngine = new Engine(null,settings,null);
```
Engine riceve tre parametri:
* **Timer realTimer**: Un timer esterno il quale esegue tick() ad ogni quanto di tempo reale
* **JSONObject startOptions**: Un oggetto contenente un JSON valido come l'esempio precedente.
* **HashMap<String,Object> constructors**: [opzionale] Una mappa che definisce quali oggetti devono essere passati come costruttore delle PerformerTask da istanziare.
Segue un esempio più completo di istanziazione di un Engine.

```java
settings = new JSONObject(loadJSONFromAsset("sampleConfig.json"));
HashMap constructors = new HashMap<String,Object>();
constructors.put("p1",new Object[]{this,R.id.contatore1});
constructors.put("p2",new Object[]{this,R.id.contatore2});
constructors.put("p3",new Object[]{getApplicationContext(),"P3: 20 tick"});
GroundTimerEx ground = new GroundTimerEx(new Handler());
tamEngine = new Engine(ground,settings,constructors);
```
Crediti
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
