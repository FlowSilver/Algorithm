"""
Standard bubble sort using list

"""

# bubble sort numbers in increasing order
def bubble_sort(lst):
    n = len(lst)
    
    for _ in range(n-1):
        for i in range(n-1):
            if lst[i] > lst[i+1]:
                swap(lst, i, i+1)  
                
# optimized bubble sort numbers in increasing order
def optimized_bubble_sort_1(lst):
    n = len(lst)
    mark = n - 1    
    
    for _ in range(n-1):
        for i in range(mark):
            if lst[i] > lst[i+1]:
                swap(lst, i, i+1)  
        mark -= 1
    
    # n = len(lst)
    # for mark in range(n-1,0,-1):
    #     for i in range(mark): 
    #         if (lst[i] > lst[i+1]):  
    #             swap(lst, i, i+1) 


# optimized bubble sort numbers using early break
def optimized_bubble_sort_2(lst):
    n = len(lst)
    mark = n - 1    
    
    for _ in range(n-1):
        swapped = False
        for i in range(mark):
            if lst[i] > lst[i+1]:
                swap(lst, i, i+1)
                swapped = True
        
        if not swapped:
            break  
        
        mark -= 1 

# swap two element in a list
def swap(lst, i, j):
    tmp = lst[i] 
    lst[i] = lst[j]
    lst[j] = tmp

if __name__ == '__main__':
    the_list = [3,2,43,1,2,4,2,3,4,2,6,87,9,9,34,54,3]
    print(the_list)
    bubble_sort(the_list)
    print(the_list)
    
    the_list = [3,2,43,1,2,4,2,3,4,2,6,87,9,9,34,54,3]
    print(the_list)
    optimized_bubble_sort_1(the_list)
    print(the_list)
    
    the_list = [3,2,43,1,2,4,2,3,4,2,6,87,9,9,34,54,3]
    print(the_list)
    optimized_bubble_sort_2(the_list)
    print(the_list)
    

