(*Question 3*)
let rec compress list = 
    match list with
    | [] -> list
    | [a] -> list
    |x::y::z -> if x = y then compress(y::z) else [x]@compress(y::z);;
(* compress  ["a";"a";"b";"c";"c";"a";"a";"d";"e";"e";"e"];; *)


(*Question 4*)
let rec validList curList list func =
    match list with 
    |[] -> curList
    |x::y ->if not(func x) then
                validList (curList@[x]) y func
            else
                validList curList y func;;

let remove_if list func = validList [] list func;;

(* remove_if [1;2;3;4;5] (fun x -> x mod 2 = 1);; *)

(*Question 5*)
let rec removeFirstN list numToRemove = 
    if numToRemove > 0 then
       match list with
       |[] -> list
       |x::y ->removeFirstN y (numToRemove - 1)
    else
        list;;

let rec takeFirstN subList list numToTake = 
    if numToTake > 0 then
        match list with 
       |[] -> subList  
       |x::y -> takeFirstN (subList@[x]) y (numToTake-1)
    else
        subList;;

let slice list start stop =
    if start >= stop then
        []
    else
        let firstStep = removeFirstN list start in
        takeFirstN [] firstStep (stop - start);;

(* slice ["a";"b";"c";"d";"e";"f";"g";"h"] 2 6;;
slice ["a";"b";"c";"d";"e";"f";"g";"h"] 3 2;;
slice ["a";"b";"c";"d";"e";"f";"g";"h"] 3 20;; *)

(*Question 6*)
let rec findEquivalence func curVal updated =
    match updated with 
    |[] -> updated@[[curVal]]
    |x::y -> match x with 
            |[] -> updated
            |a::b -> if func a curVal then [(x@[curVal])]@y
                     else [x]@(findEquivalence func curVal y);;

let rec checkEquivalence func diffVals list =
    match list with 
    |[] -> diffVals
    |x::y -> let newList = findEquivalence func x diffVals in 
             checkEquivalence func newList y;;

let equivs func list = checkEquivalence func [] list;;

(* equivs (=) [2;10];;
 equivs (fun x y -> (=) (x mod 2) (y mod 2)) [1; 2; 3; 4; 5; 6; 7; 8];; *)

(*Question 7*)
let rec isPrime num possibleDivisor =
    if possibleDivisor < (num / 2 + 1) then
        if num mod possibleDivisor = 0 then
           false
        else
            isPrime num (possibleDivisor + 1)
    else
       true

let rec findPairs pairs goal curNum =
    if curNum < (goal / 2 + 1) then
        if isPrime curNum 2 && isPrime (goal - curNum) 2 then
            
            findPairs (pairs@[(curNum, (goal-curNum))]) goal (curNum + 1)
        else
            findPairs pairs goal (curNum + 1)
   else
       pairs;;

let goldbach_pairs num =
    if num <= 2 || not(num mod 2 = 0) then
       [(-1,-1)]
    else
      findPairs [] num 3;;

(* goldbach_pairs 28;; *)

(*Question 8*)
let rec identical_on firstFunc secondFunc list = 
    match list with 
    |[] -> true
    |x::y ->if firstFunc x = secondFunc x then
              identical_on firstFunc secondFunc (y)
            else
              false;;
(* 
let f i = i * i;;
let g i = 3 * i;;
identical_on f g [3];;
identical_on f g [1; 2; 3];; *)

(*Question 9*)
let rec pow a b = 
    if b = 0 then 
       1
    else
       a * pow a (b-1);;

let rec createPolynomial func  list =
    match list with
    |[] -> func
    |x::y -> let (coeff, exponent) = x in
          createPolynomial ((fun z -> func(z) + coeff * (pow z exponent))) (y)

let polynomial list = createPolynomial (fun x -> 0) list;;
(* let f = polynomial [3, 3; -2, 1; 5, 0];;
f 2;; *)

(*Question 10*)
let rec findSuffices suffixes list = 
    match list with 
    |[] -> suffixes@[list]
    |x::y -> findSuffices (suffixes@[list]) y;;

let suffixes list = findSuffices [] list;;

(* suffixes [1; 2; 5];; *)

(*Question 11*)
let rec findSets list subSets curList = 
    match list with 
    |[] -> subSets@[curList]
    |x::y -> let firstScenario = subSets@(findSets y subSets curList) in
             firstScenario@(findSets y subSets (curList@[x]))
    ;;

let powerset input = findSets input [] [];;

(* powerset [3;4;10;11];; *)

let squares = List.fold_left (fun a b -> a + b * b) 0 (List.init 5 (fun x -> x * 2));;

let cartesian_product = let x = [1;2;3] in let y = ["a";"b";"c"] in List.concat_map(fun z -> List.map (fun j -> (z,j)) y) x;;