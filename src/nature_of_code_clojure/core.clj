(ns nature-of-code-clojure.core
  (:require [quil.core :as q]
            [quil.middleware :as m])
  (:require [nature-of-code-clojure.example :as example]
            [nature-of-code-clojure.random-walker :as random-walker]
            [nature-of-code-clojure.bouncing-ball :as bouncing-ball]
            [nature-of-code-clojure.falling-ball :as falling-ball]
            [nature-of-code-clojure.accel-towards-mouse :as atm]
            [nature-of-code-clojure.gravity-and-wind :as gaw]
            [nature-of-code-clojure.friction-cube :as fc]))

;; TODO: One day make this run a gui where you can select a simulation
(defn -main [& args]
  (println
   (str
     "Run a simulation from the REPL!\n"
     "=> (use 'nature-of-code-clojure.core)\n"
     "=> (run-[simulation name])")))

(defmacro defsimulation
  "Constructs and runs a simulation given its meta data"
  [sketch-name
   title size
   setup-fn
   update-fn
   draw-fn
   & [nav? key-pressed-fn? key-released-fn?]]
  `(q/defsketch ~(symbol sketch-name)
     :title  ~title
     :size   ~size
     ;; setup function called only once, during sketch initialization.
     :setup  ~setup-fn
     ;; update-state is called on each iteration before draw-state.
     :update ~update-fn
     :draw   ~draw-fn
     ;; functions to be called on keyboard key presses and releases
     :key-pressed  (or ~key-pressed-fn? nil)
     :key-released (or ~key-released-fn? nil)
     ;; These sketches use functional-mode middleware.
     ;; Check quil wiki for more info about middlewares and particularly
     ;; fun-mode.
     :middleware (if ~nav?
                   [m/fun-mode m/navigation-2d]
                   [m/fun-mode])))

(defn run-example
  "Runs the example simulation"
  []
  (defsimulation
    "example-simulation" "You spin my circle right round"
    [500 500]
    example/setup example/update-state example/draw-state))

(defn run-random-walker
  "Runs the random walker simulation"
  []
  (defsimulation
    "random-walker-simulation" "Random Walker"
    [500 500]
    random-walker/setup random-walker/update-state random-walker/draw-state
    true))

(defn run-bouncing-ball
  "Runs the bouncing ball simulation"
  []
  (defsimulation
    "bouncing-ball-simulation" "Bouncing Ball"
    [500 500]
    bouncing-ball/setup bouncing-ball/update-state bouncing-ball/draw-state))

(defn run-falling-ball
  "Runs the falling ball simulation"
  []
  (defsimulation
    "falling-ball-simulation" "Falling Ball"
    [500 500]
    falling-ball/setup falling-ball/update-state falling-ball/draw-state))

(defn run-accel-towards-mouse
  "Runs the accel towards mouse simulation"
  []
  (defsimulation
    "accel-towards-mouse-simulation" "Accelerate Towards Mouse"
    [1000 1000]
    atm/setup atm/update-state atm/draw-state))

(defn run-gravity-and-wind
  "Runs the gravity and wind balls simulation"
  []
  (defsimulation
   "gravity-and-wind-simulation" "Gravity and Wind"
   [1000 1000]
   gaw/setup gaw/update-state gaw/draw-state))

(defn run-friction-cube
  "Runs the friction cube simulation"
  []
  (defsimulation
    "friction-cube-simulation" "Friction Cube"
    [1000 750]
    fc/setup fc/update-state fc/draw-state
    false
    fc/on-key-down fc/on-key-up))
