print_endline("testing testing");;
(*Question 3*)
let rec contains element list =
    match list with 
    | [] -> false
    | x::y -> if x = element then true
              else contains element y;;


let rec uniques firstList secondList = 
    match secondList with
    | [] -> firstList
    | x::y -> if not (contains x firstList) then
                   uniques (firstList@[x]) y
              else uniques firstList y
let rec compress x = 
    if not (x = []) then
    uniques [List.hd x]  (List.tl x)
    else
    x;; 
compress  ["a";"a";"b";"c";"c";"a";"a";"d";"e";"e";"e"];;


(*Question 4*)
let rec validList curList list func =
    match list with 
    |[] -> curList
    |x::y ->if not(func(List.hd list)) then
                validList (curList@[x]) y func
            else
                validList curList y func;;

let remove_if list func = validList [] list func;;

remove_if [1;2;3;4;5] (fun x -> x mod 2 = 1);;

(*Question 5*)
let rec removeFirstN list numToRemove = 
    if numToRemove > 0 && List.length list > 0 then
       removeFirstN (List.tl list) (numToRemove - 1)
    else
        list;;

let rec takeFirstN subList list numToTake = 
    if numToTake > 0 && List.length list > 0 then
        takeFirstN (subList@[List.hd list]) (List.tl list) (numToTake-1)
    else
        subList;;

let slice list start stop =
    if start > stop then
        []
    else
        let firstStep = removeFirstN list start in
        takeFirstN [] firstStep (stop - start);;

slice ["a";"b";"c";"d";"e";"f";"g";"h"] 2 6;;
slice ["a";"b";"c";"d";"e";"f";"g";"h"] 3 2;;
slice ["a";"b";"c";"d";"e";"f";"g";"h"] 3 20;;

(*Question 6*)
let rec findEquivalence func curVal updated =
    match updated with 
    |[] -> updated@[[curVal]]
    |x::y -> if func (List.hd x) curVal then
             [(x@[curVal])]@y
             else
             [x]@(findEquivalence func curVal y);;

let rec checkEquivalence func diffVals list =
    match list with 
    |[] -> diffVals
    |x::y -> let newList = findEquivalence func x diffVals in 
             checkEquivalence func newList y;;

let equivs func list = checkEquivalence func [] list;;

equivs (=) [2;10];;
 equivs (fun x y -> (=) (x mod 2) (y mod 2)) [1; 2; 3; 4; 5; 6; 7; 8];;

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

goldbach_pairs 28;;

(*Question 8*)
let rec identical_on firstFunc secondFunc list = 
    match list with 
    |[] -> true
    |x::y ->if firstFunc x = secondFunc x then
              identical_on firstFunc secondFunc (y)
            else
              false;;

let f i = i * i;;
let g i = 3 * i;;
identical_on f g [3];;
identical_on f g [1; 2; 3];;

(*Question 9*)
let rec pow a b = 
    if b = 0 then 
       1
    else
       a * pow a (b-1);;

let rec createPolynomial func  list =
    if List.length list > 1 then
       let (coeff, exponent) = List.hd list in
       createPolynomial ((fun x -> func(x) + coeff * (pow x exponent))) (List.tl list)
    else 
       func;;

let polynomial list = createPolynomial (fun x -> 0) list;;
let f = polynomial [3, 3; -2, 1; 5, 0];;
f 2;;

(*Question 10*)
let rec findSuffices suffixes list = 
    match list with 
    |[] -> suffixes@[list]
    |x::y -> findSuffices (suffixes@[list]) y;;

let suffixes list = findSuffices [] list;;

suffixes [1; 2; 5];;

(*Question 11 not completed*)
let rec findSets list subSets curList = 
    match list with 
    |[] -> subSets@[curList]
    |x::y -> let firstScenario = subSets@(findSets y subSets curList) in
             firstScenario@(findSets y subSets (curList@[x]))
    ;;

let powerset input = findSets input [] [];;

powerset [3;4;10;11];;

(*Trying to sort 11*)
(* let rec findSize list size= 
    match list with 
    |[] -> size
    |x::y -> findSize y (size + 1)
    ;;

let rec findCorrectSlot test list =
    match list with 
    |[] -> list@[[test]]
    |x::y ->match x with 
            |[] -> list
            |a::b -> if findSize test 0 = findSize a 0 then [x@[test]]@y
                     else 

let rec sortBySize list sorting = 
    match list with 
    |[] -> sorting *)