def remove_if(array, function):
    newArray = []
    for x in range(len(array)):
        y = array[x]
        if(not function(y)):
            newArray.append(y)
    return newArray

print(remove_if([0,1,2,3,4],lambda x: x%2 == 1))