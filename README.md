# soundlab

overtone drawing board

## Setting up SuperCollider

Returning to this project after a while, I could not get the internal
SuperCollider server to boot with my current system (Debian Linux,
OpenJDK 12.0.1, Clojure 1.9.0, Overtone 0.10.6) - it throws an
exception. However, I got the external server to boot. Producing sound
was a little tricky as I am unfamiliar with JACK but it helped to
observe how the SuperCollider IDE configured the JACK connection graph
(open `scide` and `qjackctl`; start the SC server from within the IDE;
play a test sound to make sure it works; look at the qjackctl graph
view).

### External SC server

I am not sure if this setup is consistent for every system, but this
works for me:

1. Stop pulseaudio

2. Start JACK server:
```
jackd -r -d alsa -r 44100
```

3. Start SuperCollider server:
```
export SC_JACK_DEFAULT_INPUTS="system:capture_1,system:capture_2"
export SC_JACK_DEFAULT_OUTPUTS="system:playback_1,system:playback_2"
scsynth -u 57110 -i 2 -o 2
```
This post (https://swiki.hfbk-hamburg.de/MusicTechnology/634) helped
me figure out how to configure JACK input/outputs automatically
(SC_JACK_DEFAULT_[INPUTS|OUTPUTS]).

4. Start REPL and test sound:
```
lein repl
> (use overtone.core)
> (connect-external-server)
> (demo (saw 440))
```

## Getting started
In a REPL:
```
> (use 'soundlab.core)
> (composition)
```

To stop sound:
```
(stop)
```